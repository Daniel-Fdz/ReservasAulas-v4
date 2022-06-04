package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorGestionReservas implements Initializable {

    private IControlador controladorMVC;

    @FXML
    private TableView<Reserva> tvReservas;

    @FXML
    private TableColumn<Reserva, Profesor> tcProfesor;

    @FXML
    private TableColumn<Reserva, Aula> tcAula;

    @FXML
    private TableColumn<Reserva, Permanencia> tcPermanencia;

    @FXML
    private Button btnInsertarReserva;

    @FXML
    private Button btnAnularReserva;

    @FXML
    private Button btnBuscarReserva;

    @FXML
    private Button btnAyuda;

    @FXML
    private Button btnVolver;

    private Stage escenarioAnadirReserva;

    private ControladorAnadirReserva controladorAnadirReserva;

    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tcProfesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));
        this.tcAula.setCellValueFactory(new PropertyValueFactory<>("aula"));
        this.tcPermanencia.setCellValueFactory(new PropertyValueFactory<>("permanencia"));
        tvReservas.setItems(reservas);
    }

    public void actualizarReservas() {
        reservas.clear();
        tvReservas.getSelectionModel().clearSelection();
        reservas.setAll(controladorMVC.getReservas());
    }

    public void setControlador(IControlador controlador) {
        this.controladorMVC = controlador;
    }

    public void ayuda() {
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Información");
        dialogo.setHeaderText("Información de uso");
        dialogo.setContentText("Se pueden realizar las siguientes operaciones: \n - Insertar reserva: Inserta una nueva reserva en la tabla. Para poder realizar una reserva, primero tiene que haber un profesor registrado en el sistema. \n - Anular reserva: Anula una reserva previamente seleccionada.");
        dialogo.showAndWait();
    }

    @FXML
    public void anularReserva(ActionEvent e) {
        try {
            Reserva reserva = tvReservas.getSelectionModel().getSelectedItem();
            if (reserva != null) {
                controladorMVC.anularReserva(reserva);
                actualizarReservas();
                Dialogos.mostrarDialogoInformacion("Anulación completada", "La reserva se ha anulado con éxito");
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
    public void insertarReserva(ActionEvent e) throws IOException {
        if(escenarioAnadirReserva == null) {
            escenarioAnadirReserva = new Stage();
            FXMLLoader cargadorAnadirReserva = new FXMLLoader(LocalizadorRecursos.class.getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/AnadirReserva.fxml"));
            AnchorPane root = cargadorAnadirReserva.load();
            controladorAnadirReserva = cargadorAnadirReserva.getController();
            controladorAnadirReserva.setControlador(controladorMVC);
            controladorAnadirReserva.setReservas(reservas);

            Scene scene = new Scene(root);
            escenarioAnadirReserva.setScene(scene);
            escenarioAnadirReserva.setResizable(false);
        }
        controladorAnadirReserva.iniciar();
        escenarioAnadirReserva.show();
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