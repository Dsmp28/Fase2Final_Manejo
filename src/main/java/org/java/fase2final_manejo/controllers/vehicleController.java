package org.java.fase2final_manejo.controllers;
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
import org.java.fase2final_manejo.cells.vehicleCell;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.services.LineaService;
import org.java.fase2final_manejo.services.MarcaService;
import org.java.fase2final_manejo.services.TipoService;
import org.java.fase2final_manejo.services.VehiculoService;

import javafx.scene.control.Button;
//import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class vehicleController implements Initializable {

    private VehiculoService vehicleService;
    private MarcaService marcaService;
    private TipoService tipoService;
    private LineaService lineaService;

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private ListView lvVehiculo;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        if (vehicleService != null) {
            System.out.println("VehicleService cargado correctamente");
        } else {
            System.out.println("VehicleService no cargado");
        }
        cargarVehiculos();
    }

    @FXML
    public void buscarVehiculo(){
        //Lógica para buscar por tipo, usando el texto de txtBuscarNombre
        String nombre = txtBuscarNombre.getText();
        List<Vehiculo> vehiculos = vehicleService.buscarVehiculoPorPlaca(nombre);
        ObservableList<Vehiculo> vehiculosObservable = FXCollections.observableArrayList(vehiculos);
        lvVehiculo.setItems(vehiculosObservable);
        lvVehiculo.setCellFactory(vehicleCell -> new vehicleCell(vehiculosObservable, vehicleService));
    }
    @FXML
    public void abrirVentanaNuevoVehiculo(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fx/visuals/newVehicleView.fxml"));
//            loader.setControllerFactory(Main.context::getBean);
            Parent root = loader.load();

            Stage emergente = new Stage();
            emergente.initModality(Modality.APPLICATION_MODAL);
            emergente.initStyle(StageStyle.UNDECORATED);
            emergente.setResizable(false);
            emergente.setMaximized(false);
            emergente.setTitle("Agregar un nuevo vehículo");
            emergente.setScene(new Scene(root));
            newVehicleController controller = loader.getController();
            controller.setStage(emergente);
            emergente.showAndWait();
            cargarVehiculos();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cargarVehiculos(){
        List<Vehiculo> vehiculos = vehicleService.obtenerTodoslosVehiculos();
        ObservableList<Vehiculo> vehiculosObservable = FXCollections.observableArrayList(vehiculos);
        lvVehiculo.setItems(vehiculosObservable);
        lvVehiculo.setCellFactory(vehicleCell -> new vehicleCell(vehiculosObservable, vehicleService));
    }


}

