package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ControladorAnadirReserva implements Initializable {

    @FXML
    private TextField tfCorreo;

    @FXML
    private TextField tfNombreAula;

    @FXML
    private ChoiceBox<String> cbPermanencia;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Button btnInsertarProfesor;

    private IControlador controladorMVC;

    private ObservableList<Reserva> reservas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	cbPermanencia.setItems(FXCollections.observableArrayList("MAÑANA","TARDE","08:00","09:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"));
    }

    public void insertarReserva() {
        try {
            Profesor profesor = controladorMVC.buscarProfesor(new Profesor("Profesor", tfCorreo.getText()));
            Aula aula = controladorMVC.buscarAula(new Aula(tfNombreAula.getText(),50));
            LocalDate fecha = dpFecha.getValue();
            Permanencia permanencia = obtenerPermanencia(cbPermanencia.getValue(), fecha);

            Reserva reserva = new Reserva(profesor, aula, permanencia);
            controladorMVC.realizarReserva(reserva);
            reservas.setAll(controladorMVC.getReservas());
            Dialogos.mostrarDialogoInformacion("Información sobre reservas", "Reserva realizada correctamente");
            iniciar();
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }
    }

    private Permanencia obtenerPermanencia(String cadena, LocalDate fecha) {
        return switch (cadena) {
            case "MAÑANA" -> new PermanenciaPorTramo(fecha, Tramo.MANANA);
            case "TARDE" -> new PermanenciaPorTramo(fecha, Tramo.TARDE);
            default -> new PermanenciaPorHora(fecha, LocalTime.parse(cadena + ":00"));
        };
    }

    public void iniciar() {
        tfCorreo.setText("");
        tfNombreAula.setText("");
        cbPermanencia.getSelectionModel().select("MAÑANA");
    }

    public void setControlador(IControlador controlador) {
        this.controladorMVC = controlador;
    }

    public void setReservas(ObservableList<Reserva> reservas) {
        this.reservas = reservas;
    }
}