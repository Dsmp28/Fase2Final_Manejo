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
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.LineaService;
import org.java.fase2final_manejo.services.MarcaService;

import java.net.URL;
import java.util.ResourceBundle;

public class editLineController implements Initializable, MensajesEmergentes {

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

    private Linea linea;
    private BackupService backupService;
    private org.java.fase2final_manejo.controllers.lineController lineController;

    @FXML
    private void cerrar(){
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //backupService = Main.context.getBean(BackupService.class);
        String dataMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json";
        String indexMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt";
        marcaService = new MarcaService(new MarcaRepository(dataMarcaPath, indexMarcaPath));
        String dataLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataLinea.json";
        String indexLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexLinea.txt";
        lineaService = new LineaService(new LineaRepository(dataLineaPath, indexLineaPath));
        inicializarComboBox();
    }
    public void setLinea(Linea linea){
        this.linea = linea;
        llenarCampos();
    }
    private void llenarCampos(){
        txtLinea.setText(linea.getNombreLinea());
        txtAnio.setText(String.valueOf(linea.getAno()));
        cbMarca.setValue(linea.getMarca());
    }
    @FXML
    private void editarLinea(){
        try {
            if (cbMarca.getValue() == null || txtLinea.getText().isEmpty()){
                mostrarMensajeError("Todos los campos son obligatorios");
                return;
            }
            linea.setMarca(cbMarca.getValue());
            linea.setNombreLinea(txtLinea.getText());
            linea.setAno(Integer.parseInt(txtAnio.getText()));
            lineaService.guardarLinea(linea);
            //backupService.generateBackupInBackground();
            lineController.cargarLineas();
            mostrarMensajeExito();
            cerrar();
        }catch (Exception e){
            //Excepción
        }
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }

    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo editar la línea, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al editar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Linea editada", "La linea se edito correctamente");
    }

    private void inicializarComboBox(){
        cbMarca.getItems().addAll(marcaService.obtenerTodasLasMarcas());
    }
}
