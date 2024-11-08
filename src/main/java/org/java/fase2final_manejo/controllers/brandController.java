package org.java.fase2final_manejo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.cells.brandCell;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.services.MarcaService;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class brandController implements Initializable {

    private MarcaService marcaService;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarMarca;
    @FXML
    public ListView lvMarca = new ListView<Marca>();



    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        String dataMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json";
        String indexMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt";
        marcaService = new MarcaService(new MarcaRepository(dataMarcaPath, indexMarcaPath));
        cargarMarcas();
    }
    @FXML
    public void abrirVentanaNuevaMarca(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fase2final_manejo/visuals/newBrandView.fxml"));
            //loader.setControllerFactory(Main.context::getBean);
            Parent root = loader.load();

            Stage emergente = new Stage();
            emergente.initModality(Modality.APPLICATION_MODAL);
            emergente.initStyle(StageStyle.UNDECORATED);
            emergente.setResizable(false);
            emergente.setMaximized(false);
            emergente.setTitle("Agregar nueva marca");
            emergente.setScene(new Scene(root));
            newBrandController controller = loader.getController();
            controller.setStage(emergente);
            emergente.showAndWait();
            cargarMarcas();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void buscarMarca(){
        //Lógica para buscar una marca
        String nombreBusqueda = txtBuscarMarca.getText().trim();
        List<Marca> marcasEncontradas = marcaService.buscarMarcaPorNombre(nombreBusqueda);
        ObservableList<Marca> listaMarcas = FXCollections.observableArrayList(marcasEncontradas);
        lvMarca.setItems(listaMarcas);
        lvMarca.setCellFactory(brandCell -> new brandCell(listaMarcas, marcaService));
    }

    public void cargarMarcas(){
        lvMarca.getItems().clear();
        marcaService = new MarcaService(new MarcaRepository("src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json", "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt"));
        List<Marca> marcas = marcaService.obtenerTodasLasMarcas();
        ObservableList<Marca> listaMarcas = FXCollections.observableArrayList(marcas);
        lvMarca.setItems(listaMarcas);
        lvMarca.setCellFactory(brandCell -> new brandCell(listaMarcas, marcaService));
        lvMarca.refresh();
    }

}
