package org.java.fase2final_manejo.controllers.edits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.MarcaService;

import java.net.URL;
import java.time.LocalDate;
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
        //backupService = Main.context.getBean(BackupService.class);
    }
    @FXML
    private void editarMarca(){
//        try {
//            //Código para editar una marca
//            backupService.generateBackup(); //Esto genera el backup. Al hacer el editar agregarlo antes de mostrar el mensaje de que se edito con exito
//        } catch (Exception e) {
//            //Excepción
//        }
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }
}
