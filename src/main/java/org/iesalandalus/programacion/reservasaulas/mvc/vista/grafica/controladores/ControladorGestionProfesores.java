package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorGestionProfesores implements Initializable {

    private IControlador controladorMVC;

    @FXML
    private TableView<Profesor> tvProfesores;

    @FXML
    private TableView<Reserva> tvReservas;

    @FXML
    private TableColumn<Profesor, String> tcNombre;

    @FXML
    private TableColumn<Profesor, String> tcCorreo;

    @FXML
    private TableColumn<Profesor, String> tcTelefono;

    @FXML
    private TableColumn<Reserva, String> tcReservas;

    @FXML
    private Button btnAnadirProfesor;

    @FXML
    private Button btnBorrarProfesor;

    @FXML
    private Button btnBuscarProfesor;

    @FXML
    private Button btnAyuda;

    @FXML
    private Button btnVolver;

    private Stage escenarioAnadirProfesor;

    private ControladorAnadirProfesor controladorAnadirProfesor;

    private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
    private ObservableList<Reserva> reservasProfesor = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tvProfesores.setItems(profesores);

        this.tcReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().toString().substring(reserva.getValue().toString().indexOf(",") + 2).substring(reserva.getValue().toString().indexOf(",") + 2).substring(reserva.getValue().toString().indexOf(",") + 4)));
        tvReservas.setItems(reservasProfesor);
    }

    public void actualizarProfesores() {
        profesores.clear();
        tvProfesores.getSelectionModel().clearSelection();
        profesores.setAll(controladorMVC.getProfesores());
    }

    public void setControlador(IControlador controlador) {
        this.controladorMVC = controlador;
    }

    public void ayuda() {
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Información");
        dialogo.setHeaderText("Información de uso");
        dialogo.setContentText("Se pueden realizar las siguientes operaciones: \n - Insertar profesor: Inserta un profesor nuevo en la tabla. \n - Borrar profesor: Elimina un profesor previamente seleccionado. \n - Buscar profesor: Se puede buscar un profesor por su correo electrónico.");
        dialogo.showAndWait();
    }

    @FXML
    private void seleccion(MouseEvent e) {
        Profesor profesor = tvProfesores.getSelectionModel().getSelectedItem();
        reservasProfesor.setAll(controladorMVC.getReservasProfesor(profesor));
        if(profesor != null) {
            tcReservas.setText(profesor.getNombre());
        }
    }

    @FXML
    private void buscarProfesor(ActionEvent e) {
        String correoProfesor = Dialogos.mostrarDialogoTexto("Búsqueda", "Profesor a buscar (correo):");
        try {
            Profesor profesor = controladorMVC.buscarProfesor(new Profesor("A A", correoProfesor));
            if (profesor == null) {
                Dialogos.mostrarDialogoInformacion("Profesor no encontrado", "No hay ningún profesor con ese correo");
            } else {
                Dialogos.mostrarDialogoInformacion(profesor.getNombre(), profesor.toString());
                tvProfesores.getSelectionModel().select(profesor);
            }
        } catch(Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    public void borrarProfesor(ActionEvent e) {
        try {
            Profesor profesor = tvProfesores.getSelectionModel().getSelectedItem();
            if (profesor != null) {
                controladorMVC.borrarProfesor(profesor);
                actualizarProfesores();
                Dialogos.mostrarDialogoInformacion("Eliminación completada", "El profesor ha sido borrado con éxito");
            }
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    public void anadirProfesor(ActionEvent e) throws IOException {
        if(escenarioAnadirProfesor == null) {
            escenarioAnadirProfesor = new Stage();
            FXMLLoader cargadorAnadirProfesor = new FXMLLoader(LocalizadorRecursos.class.getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/AnadirProfesor.fxml"));
            Pane root = cargadorAnadirProfesor.load();
            controladorAnadirProfesor = cargadorAnadirProfesor.getController();
            controladorAnadirProfesor.setControlador(controladorMVC);
            controladorAnadirProfesor.setProfesores(profesores);

            Scene scene = new Scene(root);
            escenarioAnadirProfesor.setScene(scene);
            escenarioAnadirProfesor.setResizable(false);
        }
        controladorAnadirProfesor.iniciar();
        escenarioAnadirProfesor.show();
    }

    public void acercaDe(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/AcercaDe.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void cerrarVentana() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    public void cerrarAplicacion(ActionEvent e) {
        Alert dialogoCierre = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoCierre.setTitle("Cerrar la aplicación");
        dialogoCierre.setHeaderText(null);
        dialogoCierre.setContentText("¿Estás seguro de querer cerrar la aplicación?");

        Optional<ButtonType> respuesta = dialogoCierre.showAndWait();
        if(respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}