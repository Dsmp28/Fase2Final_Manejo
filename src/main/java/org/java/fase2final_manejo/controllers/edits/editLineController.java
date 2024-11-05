package org.java.fase2final_manejo.controllers.edits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
//        try {
//            //Código para editar una línea
//            backupService.generateBackup(); //Esto genera el backup. Al hacer el editar agregarlo antes de mostrar el mensaje de que se edito con exito
//        }catch (Exception e){
//            //Excepción
//        }
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }
}
