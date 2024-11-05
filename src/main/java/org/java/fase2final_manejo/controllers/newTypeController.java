package org.java.fase2final_manejo.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.repositories.VehiculoRepository;
import org.java.fase2final_manejo.services.*;


import java.net.URL;
import java.util.ResourceBundle;


public class newTypeController implements Initializable, MensajesEmergentes {
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtAnio;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnCerrar;

    TipoService tipoService;

    private Stage stage;

    private BackupService backupService;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @FXML
    private void agregarNuevo(){
        try {
            String nombreTipo = txtTipo.getText();
            Integer anio = Integer.parseInt(txtAnio.getText());
            if (nombreTipo.isEmpty()){
                throw new Exception("Todos los campos son obligatorios");
            }
            tipoService.guardarTipo(new Tipo(nombreTipo, anio));
//            backupService.generateBackup();
//            backupService.generateBackupInBackground();
            mostrarMensajeExito();
            cerrar();
        }catch (Exception e){
            mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        backupService = Main.context.getBean(BackupService.class);
        String dataTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/dataTipo.json";
        String indexTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/indexTipo.txt";

        tipoService = new TipoService(new TipoRepository(dataTipoPath, indexTipoPath));
    }

    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo guardar el tipo, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al guardar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Tipo guardado", "El tipo se guardo correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage= emergente;
    }
}
