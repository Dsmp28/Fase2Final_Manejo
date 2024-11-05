package org.java.fase2final_manejo.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.cells.typeCell;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.services.MarcaService;
import org.java.fase2final_manejo.services.TipoService;



import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class typeController implements Initializable {

    private TipoService tipoService;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private ListView<Tipo> lvTipo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String dataTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/dataTipo.json";
        String indexTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/indexTipo.txt";
        tipoService = new TipoService(new TipoRepository(dataTipoPath, indexTipoPath));
        cargarTipos();
    }

    @FXML
    public void abrirVentanaNuevoTipo(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fx/visuals/newTypeView.fxml"));
//            loader.setControllerFactory(Main.context::getBean);
            Parent root = loader.load();

            Stage emergente = new Stage();
            emergente.initModality(Modality.APPLICATION_MODAL);
            emergente.initStyle(StageStyle.UNDECORATED);
            emergente.setResizable(false);
            emergente.setMaximized(false);
            emergente.setTitle("Agregar nuevo tipo");
            emergente.setScene(new Scene(root));
            newTypeController controller = loader.getController();
            controller.setStage(emergente);
            emergente.showAndWait();
            cargarTipos();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void buscarTipo(){
        //Lógica para buscar por tipo, usando el texto de txtBuscarNombre
        String nombreBusqueda = txtBuscarNombre.getText().trim();
        List<Tipo> tipos = tipoService.buscarTipoPorNombre(nombreBusqueda);
        ObservableList<Tipo> observableList = FXCollections.observableArrayList(tipos);
        lvTipo.setItems(observableList);
        lvTipo.setCellFactory(typeCell -> new typeCell(observableList, tipoService));

    }

    public void cargarTipos(){
        List<Tipo> tipos = tipoService.obtenerTodoslosTipos();
        ObservableList<Tipo> observableList = FXCollections.observableArrayList(tipos);
        lvTipo.setItems(observableList);

        // Pasa la lista observable al constructor de typeCell
        lvTipo.setCellFactory(typeCell -> new typeCell(observableList, tipoService));
    }

}
