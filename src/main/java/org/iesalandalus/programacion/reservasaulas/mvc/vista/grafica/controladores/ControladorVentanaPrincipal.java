package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;

public class ControladorVentanaPrincipal implements Initializable {

    private IControlador controladorMVC;

    @FXML
    private Button btnOperacionesAulas;

    @FXML
    private Button btnOperacionesProfesores;

    @FXML
    private Button btnOperacionesReservas;

    @FXML
    private Button btnConsultarDisponibilidad;

    private Stage escenarioGestionAulas;

    private Stage escenarioGestionProfesores;

    private Stage escenarioGestionReservas;

    private ControladorGestionAulas controladorGestionAulas;

    private ControladorGestionProfesores controladorGestionProfesores;

    private ControladorGestionReservas controladorGestionReservas;
    
    private Image icono = new Image("org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/imagenes/booking.png");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

    public void setControladorMVC(IControlador controladorMVC) {
        this.controladorMVC = controladorMVC;
    }

    public void operacionesAulas(ActionEvent e) throws IOException {
        if(escenarioGestionAulas == null) {
            escenarioGestionAulas = new Stage();
            FXMLLoader cargadorGestionAulas = new FXMLLoader(LocalizadorRecursos.class.getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/GestionAulas.fxml"));
            Pane root = cargadorGestionAulas.load();
            controladorGestionAulas = cargadorGestionAulas.getController();
            controladorGestionAulas.setControlador(controladorMVC);

            Scene scene = new Scene(root);
            escenarioGestionAulas.getIcons().add(icono);
            escenarioGestionAulas.setTitle("Gestión de aulas");
            escenarioGestionAulas.setScene(scene);
            escenarioGestionAulas.setResizable(false);
        }
        controladorGestionAulas.actualizarAulas();
        escenarioGestionAulas.show();
    }



    public void operacionesProfesores(ActionEvent e) throws IOException {
        if(escenarioGestionProfesores == null) {
            escenarioGestionProfesores = new Stage();
            FXMLLoader cargadorGestionProfesores = new FXMLLoader(LocalizadorRecursos.class.getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/GestionProfesores.fxml"));
            AnchorPane root = cargadorGestionProfesores.load();
            controladorGestionProfesores = cargadorGestionProfesores.getController();
            controladorGestionProfesores.setControlador(controladorMVC);

            Scene scene = new Scene(root);
            escenarioGestionProfesores.getIcons().add(icono);
            escenarioGestionProfesores.setTitle("Gestión de profesores");
            escenarioGestionProfesores.setScene(scene);
            escenarioGestionProfesores.setResizable(false);
        }
        controladorGestionProfesores.actualizarProfesores();
        escenarioGestionProfesores.show();
    }

    public void operacionesReservas(ActionEvent e) throws IOException {
        if(escenarioGestionReservas == null) {
            escenarioGestionReservas = new Stage();
            FXMLLoader cargadorGestionReservas = new FXMLLoader(LocalizadorRecursos.class.getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/GestionReservas.fxml"));
            AnchorPane root = cargadorGestionReservas.load();
            controladorGestionReservas = cargadorGestionReservas.getController();
            controladorGestionReservas.setControlador(controladorMVC);

            Scene scene = new Scene(root);
            escenarioGestionReservas.getIcons().add(icono);
            escenarioGestionReservas.setTitle("Gestión de reservas");
            escenarioGestionReservas.setScene(scene);
            escenarioGestionReservas.setResizable(false);
        }
        controladorGestionReservas.actualizarReservas();
        escenarioGestionReservas.show();
    }

    public void consultarDisponibilidad(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/ConsultarDisponibilidad.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void acercaDe(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/AcercaDe.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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