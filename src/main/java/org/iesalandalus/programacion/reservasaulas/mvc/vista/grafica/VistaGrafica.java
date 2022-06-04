package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores.ControladorVentanaPrincipal;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;


public class VistaGrafica extends Application implements IVista {

    private static IControlador controladorMVC;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader cargadorPrincipal = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/VentanaPrincipal.fxml"));
            Pane root = cargadorPrincipal.load();
            Image icono = new Image("org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/imagenes/booking.png");
            ControladorVentanaPrincipal controladorPrincipal = cargadorPrincipal.getController();
            controladorPrincipal.setControladorMVC(controladorMVC);
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(icono);
            primaryStage.setOnCloseRequest(e -> confirmarSalida(primaryStage, e));
            primaryStage.setTitle("Reserva de aulas V. 1.0");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setControlador(IControlador control) {
        controladorMVC = control;
    }

    @Override
    public void comenzar() {
        launch(this.getClass());
    }

    @Override
    public void salir() {
        controladorMVC.terminar();
    }
    
    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
        if (Dialogos.mostrarDialogoConfirmacion("Cerrar la aplicación", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
            controladorMVC.terminar();
            escenarioPrincipal.close();
        }
        else {
            e.consume();
        }
    }
}