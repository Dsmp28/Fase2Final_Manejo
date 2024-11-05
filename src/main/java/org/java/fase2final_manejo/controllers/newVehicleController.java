package org.java.fase2final_manejo.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.services.*;

import java.net.URL;
import java.util.ResourceBundle;

public class newVehicleController implements Initializable, MensajesEmergentes {
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

    private MarcaService marcaService;

    private LineaService lineaService;

    private TipoService tipoService;

    private VehiculoService vehiculoService;

    private BackupService backupService;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @FXML
    private void agregarNuevo(){
        try {
            String modelo = txtModelo.getText();
            String color = txtColor.getText();
            String placa = txtPlaca.getText();
            String chasis = txtChasis.getText();
            String motor = txtMotor.getText();
            String vin = txtVin.getText();
            Integer numAsientos = Integer.parseInt(txtNumAsientos.getText());
            Marca marca = cbMarca.getValue();
            Tipo tipo = cbTipo.getValue();
            Linea linea = cbLinea.getValue();
            if (modelo.isEmpty() || color.isEmpty() || placa.isEmpty() || chasis.isEmpty() || motor.isEmpty() || vin.isEmpty() || marca == null || tipo == null || linea == null){
                throw new Exception("Todos los campos son obligatorios");
            }
            Vehiculo vehiculo = new Vehiculo(marca, modelo, color, placa, chasis, motor, vin, numAsientos, tipo, linea);
            vehiculoService.guardarVehiculo(vehiculo);
//            backupService.generateBackupInBackground();
            mostrarMensajeExito();
            cerrar();
        } catch (Exception e){
            mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        backupService = Main.context.getBean(BackupService.class);
        cargarMarcas();
        cargarTipos();
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