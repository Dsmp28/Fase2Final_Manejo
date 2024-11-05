package org.java.fase2final_manejo.controllers.edits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.services.*;


import java.net.URL;
import java.util.ResourceBundle;

public class editVehicleController implements Initializable, MensajesEmergentes {
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtChasis;
    @FXML
    private TextField txtMotor;
    @FXML
    private TextField txtVin;
    @FXML
    private ComboBox<Marca> cbMarca;
    @FXML
    private ComboBox<Tipo> cbTipo;
    @FXML
    private ComboBox<Linea> cbLinea;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnCerrar;
    @FXML
    private TextField txtNumAsientos;
    private Stage stage;

    private Vehiculo vehiculo;

    private MarcaService marcaService;

    private LineaService lineaService;

    private TipoService tipoService;

    private VehiculoService vehiculoService;

    private BackupService backupService;
    private org.java.fase2final_manejo.controllers.vehicleController vehicleController;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        backupService = Main.context.getBean(BackupService.class);
    }
    @FXML
    private void editarVehiculo(){
        try{
            if (txtModelo.getText().isEmpty() || txtColor.getText().isEmpty() || txtPlaca.getText().isEmpty() || txtChasis.getText().isEmpty() || txtMotor.getText().isEmpty() || txtVin.getText().isEmpty() || txtNumAsientos.getText().isEmpty() || cbMarca.getValue() == null || cbTipo.getValue() == null || cbLinea.getValue() == null){
                mostrarMensajeError("Todos los campos son obligatorios");
                return;
            }
            vehiculo.setModelo(txtModelo.getText());
            vehiculo.setColor(txtColor.getText());
            vehiculo.setPlaca(txtPlaca.getText());
            vehiculo.setChasis(txtChasis.getText());
            vehiculo.setMotor(txtMotor.getText());
            vehiculo.setVin(txtVin.getText());
            vehiculo.setAsientos(Integer.parseInt(txtNumAsientos.getText()));
            vehiculo.setMarca(cbMarca.getValue());
            vehiculo.setTipo(cbTipo.getValue());
            vehiculoService.guardarVehiculo(vehiculo);
//            backupService.generateBackupInBackground(); //Esto genera el backup. Al hacer el editar agregarlo antes de mostrar el mensaje de que se edito con exito
            vehicleController.cargarVehiculos();
            mostrarMensajeExito();
            cerrar();
        }catch (Exception e){
            mostrarMensajeError("Revise que los campos no sean repetidos con otros valores existentes");
        }
    }

    public void setVehiculo(Vehiculo vehiculo){
        cargarMarcas();
        cargarTipos();
        this.vehiculo = vehiculo;
        llenarCampos();
    }
    private void llenarCampos(){
        txtModelo.setText(vehiculo.getModelo());
        txtColor.setText(vehiculo.getColor());
        txtPlaca.setText(vehiculo.getPlaca());
        txtChasis.setText(vehiculo.getChasis());
        txtMotor.setText(vehiculo.getMotor());
        txtVin.setText(vehiculo.getVin());
        txtNumAsientos.setText(String.valueOf(vehiculo.getAsientos()));
        cbMarca.setValue(vehiculo.getMarca());
        cbTipo.setValue(vehiculo.getTipo());
        cbLinea.setValue(vehiculo.getLinea());
    }
    private void cargarMarcas(){
        cbMarca.getItems().addAll(marcaService.obtenerTodasLasMarcas());
    }

    public void cargarTipos(){
        cbTipo.getItems().addAll(tipoService.obtenerTodoslosTipos());
    }
    @FXML
    private void cargarLineas(){
        cbLinea.getItems().clear();
        cbLinea.getItems().addAll(lineaService.obtenerLineaPorMarca(cbMarca.getValue().getId()));
    }

    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo guardar el vehiculo, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al guardar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Vehiculo guardado", "El vehiculo se guardo correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage= emergente;
    }
}