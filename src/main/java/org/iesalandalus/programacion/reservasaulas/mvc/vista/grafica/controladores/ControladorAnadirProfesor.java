package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

public class ControladorAnadirProfesor {

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfCorreo;

    @FXML
    private TextField tfTelefono;

    @FXML
    private Button btnInsertarProfesor;

    private IControlador controlador;

    private ObservableList<Profesor> profesores;

    // Inserta un profesor y devuelve una alerta en caso de excepción
    @FXML
    public void insertarProfesor() {
        try {
            Profesor profesor = new Profesor(tfNombre.getText(), tfCorreo.getText(), tfTelefono.getText());
            controlador.insertarProfesor(profesor);
            profesores.setAll(controlador.getProfesores());
            Dialogos.mostrarDialogoInformacion("Información sobre profesores", "Profesor insertado correctamente");
            iniciar();
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }

    }

    // Vacía al inicio los campos de entrada de texto 
    public void iniciar() {
        tfNombre.setText("");
        tfCorreo.setText("");
        tfTelefono.setText("");
    }

    // Pasamos el controlador al controlador de la vista
    public void setControlador(IControlador controlador) {
        this.controlador = controlador;
    }

    // Se pasa como parámetro un ObservableList de profesores y este se le asigna al ObservableList de la clase
    public void setProfesores(ObservableList<Profesor> profesores) {
        this.profesores = profesores;
    }
}