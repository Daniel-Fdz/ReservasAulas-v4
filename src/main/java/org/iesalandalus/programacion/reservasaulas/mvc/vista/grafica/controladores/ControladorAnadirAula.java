package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;


public class ControladorAnadirAula {

    @FXML
    private TextField tfAula;

    @FXML
    private TextField tfCapacidad;

    @FXML
    private Button btnInsertarAula;

    private IControlador controladorMVC;
    
    private ObservableList<Aula> aulas;

    // Inserta un aula y devuelve una alerta en caso de excepción
    public void insertarAula() {
        try {
            Aula aula = new Aula(tfAula.getText(), Integer.parseInt(tfCapacidad.getText()));
            controladorMVC.insertarAula(aula);
            aulas.setAll(controladorMVC.getAulas());
            Dialogos.mostrarDialogoInformacion("Información sobre aulas", "Aula insertada correctamente");
            iniciar();
        } catch (NumberFormatException ex) {
        	Dialogos.mostrarDialogoError("Error", "ERROR: El valor del campo \"capacidad\" no es correcto.");
        } catch (Exception ex2) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(ex2.getMessage());
            alerta.showAndWait();
        }
    }

    // Vacía al inicio los campos de entrada de texto 
    public void iniciar() {
        tfAula.setText("");
        tfCapacidad.setText("");
    }

    // Pasamos el controlador al controlador de la vista
    public void setControlador(IControlador controlador) {
        this.controladorMVC = controlador;
    }

    // Se pasa como parámetro un ObservableList de Aulas y este se le asigna al ObservableList de la clase
    public void setAulas(ObservableList<Aula> aulas) {
        this.aulas = aulas;
    }
}