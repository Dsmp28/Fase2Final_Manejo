package org.java.fase2final_manejo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.LineaService;
import org.java.fase2final_manejo.services.MarcaService;
import java.net.URL;
import java.util.ResourceBundle;

public class newLineController implements Initializable, MensajesEmergentes {
    @FXML
    private TextField txtLinea;
    @FXML
    private TextField txtAnio;
    @FXML
    private ComboBox<Marca> cbMarca;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnCerrar;

    private Stage stage;

    private MarcaService marcaService;

    private LineaService lineaService;

    private BackupService backupService;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @FXML
    private void agregarNuevo(){
        try {
            Marca marca = cbMarca.getValue();
            String nombreLinea = txtLinea.getText();
            Integer anio = Integer.parseInt(txtAnio.getText());
            if (marca == null || nombreLinea.isEmpty()){
                throw new Exception("Todos los campos son obligatorios");
            }
            Linea linea = new Linea(marca, nombreLinea, anio);
            lineaService.guardarLinea(linea);
            //backupService.generateBackup();
            mostrarMensajeExito();
            cerrar();
        }catch (Exception e){
            mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //backupService = Main.context.getBean(BackupService.class);
        cargarMarcas();
    }

    private void cargarMarcas(){
        cbMarca.getItems().addAll(marcaService.obtenerTodasLasMarcas());
    }


    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo guardar la línea, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al guardar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Linea guardada", "La linea se guardo correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }
}