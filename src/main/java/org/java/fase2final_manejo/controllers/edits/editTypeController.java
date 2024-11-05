package org.java.fase2final_manejo.controllers.edits;
//import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.TipoService;


import java.net.URL;
import java.util.ResourceBundle;

public class editTypeController implements Initializable, MensajesEmergentes {
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

    private Tipo tipo;
    private BackupService backupService;
    private org.java.fase2final_manejo.controllers.typeController typeController;

    @FXML
    private void cerrar(){
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        backupService = Main.context.getBean(BackupService.class);
        String dataTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/dataTipo.json";
        String indexTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/indexTipo.txt";
        tipoService = new TipoService(new TipoRepository(dataTipoPath, indexTipoPath));
    }
    @FXML
    private void editarTipo(){
        try {
            if (txtTipo.getText().isEmpty() || txtAnio.getText().isEmpty()) {
                mostrarMensajeError("Todos los campos son obligatorios");
                return;
            }
            tipo.setNombreTipo(txtTipo.getText());
            tipo.setAno(Integer.parseInt(txtAnio.getText()));
            tipoService.guardarTipo(tipo);
//            backupService.generateBackupInBackground(); //Esto genera el backup. Al hacer el editar agregarlo antes de mostrar el mensaje de que se edito con exito
            typeController.cargarTipos();
            mostrarMensajeExito();
            cerrar();
        }catch (Exception e){
            //Mostrar mensaje de error
        }
    }
    public void setTipo(Tipo tipo){
        this.tipo = tipo;
        llenarCampos();
    }
    private void llenarCampos(){
        txtTipo.setText(tipo.getNombreTipo());
        txtAnio.setText(String.valueOf(tipo.getAno()));
    }

    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo editar el tipo, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al editar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Tipo editado", "El tipo se edito correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage= emergente;
    }
}
