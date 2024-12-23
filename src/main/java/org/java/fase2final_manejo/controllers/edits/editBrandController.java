package org.java.fase2final_manejo.controllers.edits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.MarcaService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class editBrandController implements Initializable, MensajesEmergentes {
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

    private Marca marca;
    private BackupService backupService;
    private org.java.fase2final_manejo.controllers.brandController brandController;

    @FXML
    private void cerrar(){
        stage.close();
    }

    public void setMarca(Marca marca){
        this.marca = marca;
        llenarCampos();
    }

    private void llenarCampos(){
        txtMarca.setText(marca.getNombre());
        txtFundador.setText(marca.getFundador());
        dpFecha.setValue(marca.getFechaCreacion());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backupService = new BackupService();
        String dataMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json";
        String indexMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt";
        marcaService = new MarcaService(new MarcaRepository(dataMarcaPath, indexMarcaPath));
        brandController = new org.java.fase2final_manejo.controllers.brandController();
    }
    @FXML
    private void editarMarca(){
        try {
            if (txtMarca.getText().isEmpty() || txtFundador.getText().isEmpty() || dpFecha.getValue() == null) {
                mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Campos vacios", "Por favor llene todos los campos");
                return;
            }
            // Verificar si ya existe una marca con el mismo nombre y diferente ID
            List<Marca> marcasExistentes = marcaService.buscarMarcaPorNombre(txtMarca.getText());
            for (Marca marcaExistente : marcasExistentes) {
                if (!marcaExistente.getId().equals(marca.getId()) && marcaExistente.getNombre().equalsIgnoreCase(txtMarca.getText())) {
                    mostrarMensajeError("Ya existe una marca con el nombre ingresado.");
                    return;
                }
            }

            marca.setNombre(txtMarca.getText());
            marca.setFundador(txtFundador.getText());
            marca.setFechaCreacion(dpFecha.getValue());
            marcaService.guardarMarca(marca);
            backupService.generateBackupInBackground();
            brandController.cargarMarcas();
            mostrarMensajeExito();
            cerrar();
            brandController.lvMarca.getItems().clear();
            brandController.cargarMarcas();
        } catch (Exception e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }
    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo editar la marca, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al editar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Marca editada", "La marca se edito correctamente");
    }
}
