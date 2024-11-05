package org.java.fase2final_manejo.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.cells.lineaCell;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.services.LineaService;
import org.java.fase2final_manejo.services.MarcaService;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class lineController implements Initializable {

    private LineaService lineaService;

    private MarcaService marcaService;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarLinea;
    @FXML
    private ListView<Linea> lvLinea;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        String dataLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataLinea.json";
        String indexLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexLinea.txt";
        lineaService = new LineaService(new LineaRepository(dataLineaPath, indexLineaPath));
        String dataMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json";
        String indexMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt";
        marcaService = new MarcaService(new MarcaRepository(dataMarcaPath, indexMarcaPath));
        cargarLineas();
    }

    @FXML
    public void buscarLinea(){
        String nombreLinea = txtBuscarLinea.getText().trim();
        List<Linea> lineas = lineaService.buscarLineaPorNombre(nombreLinea);
        ObservableList<Linea> observableList = FXCollections.observableArrayList(lineas);
        lvLinea.setItems(observableList);
        lvLinea.setCellFactory(lineaCell -> new lineaCell(observableList, lineaService));

    }
    @FXML
    public void abrirVentanaNuevaLinea(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fase2final_manejo/visuals/newLineView.fxml"));
            //loader.setControllerFactory(Main.context::getBean);
            Parent root = loader.load();

            Stage emergente = new Stage();
            emergente.initModality(Modality.APPLICATION_MODAL);
            emergente.initStyle(StageStyle.UNDECORATED);
            emergente.setResizable(false);
            emergente.setMaximized(false);
            emergente.setTitle("Agregar nueva linea");
            emergente.setScene(new Scene(root));
            newLineController controller = loader.getController();
            controller.setStage(emergente);
            emergente.showAndWait();
            cargarLineas();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cargarLineas(){
        List<Linea> lineas = lineaService.obtenerTodoslasLineas();

        ObservableList<Linea> observableList = FXCollections.observableArrayList(lineas);
        lvLinea.setItems(observableList);

        lvLinea.setCellFactory(lineaCell -> new lineaCell(observableList, lineaService));
    }

}