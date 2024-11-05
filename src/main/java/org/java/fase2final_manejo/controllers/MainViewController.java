package org.java.fase2final_manejo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnMarca;
    @FXML
    private Button btnTipo;
    @FXML
    private Button btnLinea;
    @FXML
    private Button btnVehiculos;
    @FXML
    private Button btnSalir;
    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        botonActivo(btnInicio, "homeView.fxml");
    }

    private void botonActivo(Button activo, String fxml){
        reiniciarEstilos();
        activo.setStyle("-fx-background-color: rgba(190,228,215,0.89);" + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.65), 5, 0, -1, 0);");
        cargarFxml(fxml);
    }

    private void cargarFxml(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/fase2final_manejo/visuals/" + fxml));

            Parent root = fxmlLoader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reiniciarEstilos(){
        btnInicio.setStyle("-fx-box-shadow: 0px 0px 0px 0px rgba(0,0,0,0.1);");
        btnInicio.setStyle("-fx-background-color: rgba(190,228,215, 0)");
        btnMarca.setStyle("-fx-box-shadow: 0px 0px 0px 0px rgba(0,0,0,0.1);");
        btnMarca.setStyle("-fx-background-color: rgba(190,228,215, 0)");
        btnTipo.setStyle("-fx-box-shadow: 0px 0px 0px 0px rgba(0,0,0,0.1);");
        btnTipo.setStyle("-fx-background-color: rgba(190,228,215, 0)");
        btnLinea.setStyle("-fx-box-shadow: 0px 0px 0px 0px rgba(0,0,0,0.1);");
        btnLinea.setStyle("-fx-background-color: rgba(190,228,215, 0)");
        btnVehiculos.setStyle("-fx-box-shadow: 0px 0px 0px 0px rgba(0,0,0,0.1);");
        btnVehiculos.setStyle("-fx-background-color: rgba(190,228,215, 0)");
    }

    @FXML
    public void btnInicio() {
        botonActivo(btnInicio, "homeView.fxml");
    }

    @FXML
    public void btnMarca() {
        botonActivo(btnMarca, "brandView.fxml");
    }

    @FXML
    public void btnTipo() {
        botonActivo(btnTipo, "typeView.fxml");
    }

    @FXML
    public void btnLinea() {
        botonActivo(btnLinea, "lineView.fxml");
    }

    @FXML
    public void btnVehiculos() {
        botonActivo(btnVehiculos, "vehicleView.fxml");
    }

    @FXML
    public void btnSalir() {
        salir();
    }

    private void salir(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("¿Desea salir de la aplicación?");
        alert.setGraphic(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        }
    }
}
