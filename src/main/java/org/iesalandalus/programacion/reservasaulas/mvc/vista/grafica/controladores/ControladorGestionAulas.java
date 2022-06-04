package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

public class ControladorGestionAulas implements Initializable {

    private IControlador controladorMVC;

    @FXML
    private TableView<Aula> tvAulas;

    @FXML
    private TableColumn<Aula, String> tcNombre;

    @FXML
    private TableColumn<Aula, Integer> tcCapacidad;

    @FXML
    private Button btnAnadirAula;
    @FXML
    private Button btnBorrarAula;
    @FXML
    private Button btnBuscarAula;

    @FXML
    private Button btnConsultarDisp;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> cbPermanencia;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField tfNombre;

    private Stage escenarioAnadirAula;
    private ControladorAnadirAula controladorAnadirAula;
    private ObservableList<Aula> aulas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.tcCapacidad.setCellValueFactory(new PropertyValueFactory<>("puestos"));
        tvAulas.setItems(aulas);
        cbPermanencia.getSelectionModel().select("MAÑANA");
        btnConsultarDisp.setDisable(true);
        iniciar();
    }

    @FXML
    private void habilitarBoton() {
        if(tfNombre.getText() != null && dpFecha.getValue() != null) {
            btnConsultarDisp.setDisable(false);
        }
    }

    private Permanencia obtenerPermanencia(String cadena, LocalDate fecha) {
        return switch (cadena) {
            case "MAÑANA" -> new PermanenciaPorTramo(fecha, Tramo.MANANA);
            case "TARDE" -> new PermanenciaPorTramo(fecha, Tramo.TARDE);
            default -> new PermanenciaPorHora(fecha, LocalTime.parse(cadena + ":00"));
        };
    }

    @FXML
    private void consultarDisponibilidadReservas() {
        try {
            String nombre = tfNombre.getText();
            LocalDate fecha = dpFecha.getValue();
            Permanencia permanencia = obtenerPermanencia(cbPermanencia.getValue(), fecha);

            if(controladorMVC.consultarDisponibilidad(controladorMVC.buscarAula(new Aula(nombre, 50)), permanencia)) {
                Dialogos.mostrarDialogoInformacion("Información sobre aulas", "El aula se encuentra disponible para reservar");
            } else {
                Dialogos.mostrarDialogoInformacion("Información sobre aulas", "El aula no se encuentra disponible para reservar");
            }
        } catch(Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }
    }

    public void iniciar() {
        cbPermanencia.setItems(FXCollections.observableArrayList("MAÑANA","TARDE","08:00","09:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"));
    }

    public void actualizarAulas() {
        aulas.clear();
        tvAulas.getSelectionModel().clearSelection();
        aulas.setAll(controladorMVC.getAulas());
    }

    public void setControlador(IControlador controlador) {
        this.controladorMVC = controlador;
    }

    public void ayuda() {
        Alert dialogo = new Alert(Alert.AlertType.INFORMATION);
        dialogo.setTitle("Información");
        dialogo.setHeaderText("Información de uso");
        dialogo.setContentText("Se pueden realizar las siguientes operaciones: \n - Insertar aula: Inserta un aula nueva en la tabla. \n - Borrar aula: Elimina un aula previamente seleccionada. \n - Buscar aula: Se puede buscar una aula por su nombre.");
        dialogo.showAndWait();
    }

    @FXML
    private void buscarAula(ActionEvent e) {
        String nombreAula = Dialogos.mostrarDialogoTexto("Búsqueda", "Aula a buscar:");
        try {
            Aula aula = controladorMVC.buscarAula(new Aula(nombreAula, 50));
            if (aula == null) {
                Dialogos.mostrarDialogoInformacion("Aula no encontrada", "No hay ningún aula con ese nombre");
            } else {
                Dialogos.mostrarDialogoInformacion(aula.getNombre(), aula.toString());
                tvAulas.getSelectionModel().select(aula);
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
    public void borrarAula(ActionEvent e) {
        try {
            Aula aula = tvAulas.getSelectionModel().getSelectedItem();
            if (aula != null) {
                controladorMVC.borrarAula(aula);
                actualizarAulas();
                Dialogos.mostrarDialogoInformacion("Eliminación completada", "El aula ha sido borrada con éxito");
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
    public void anadirAula(ActionEvent e) throws IOException {
        if(escenarioAnadirAula == null) {
            escenarioAnadirAula = new Stage();
            FXMLLoader cargadorAnadirAula = new FXMLLoader(LocalizadorRecursos.class.getResource("/org/iesalandalus/programacion/reservasaulas/mvc/vista/grafica/recursos/vistas/AnadirAula.fxml"));
            Pane root = cargadorAnadirAula.load();
            controladorAnadirAula = cargadorAnadirAula.getController();
            controladorAnadirAula.setControlador(controladorMVC);
            controladorAnadirAula.setAulas(aulas);

            Scene scene = new Scene(root);
            escenarioAnadirAula.setScene(scene);
            escenarioAnadirAula.setResizable(false);
        }
        controladorAnadirAula.iniciar();
        escenarioAnadirAula.show();
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