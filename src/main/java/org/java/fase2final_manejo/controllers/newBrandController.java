package org.java.fase2final_manejo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.MarcaService;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class newBrandController implements Initializable, MensajesEmergentes {
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtFundador;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnCerrar;
    private MarcaService marcaService;
    private Stage stage;

    private BackupService backupService;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @FXML
    private void agregarNuevo(){
        try {
            String nombreMarca = txtMarca.getText();
            String fundador = txtFundador.getText();
            LocalDate fecha = dpFecha.getValue();
            if (nombreMarca.isEmpty() || fundador.isEmpty() || fecha == null){
                throw new Exception("Todos los campos son obligatorios");
            }
            Marca marca = new Marca(nombreMarca, fecha, fundador);
            marcaService.guardarMarca(marca);
            //backupService.generateBackup();
            mostrarMensajeExito();
            cerrar();
        } catch (Exception e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //backupService = Main.context.getBean(BackupService.class);
    }

    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo guardar la marca, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al guardar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Marca guardada", "La marca se guardo correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }
}