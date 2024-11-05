package org.java.fase2final_manejo.cells;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import org.hibernate.mapping.List;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.controllers.edits.editVehicleController;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.VehiculoService;

import java.io.File;
import java.io.IOException;

public class vehicleCell extends ListCell<Vehiculo> implements MensajesEmergentes {
    private final HBox content;
    private final Text Marca;
    private final Text Tipo;
    private final Text Linea;
    private final Text Modelo;
    private final Text Placa;
    private final Text Color;
    private final Text Motor;
    private final Text Chasis;
    private final Text Vin;
    private final StackPane paneMarca;
    private final StackPane paneTipo;
    private final StackPane paneLinea;
    private final StackPane paneModelo;
    private final StackPane panePlaca;
    private final StackPane paneColor;
    private final StackPane paneMotor;
    private final StackPane paneChasis;
    private final StackPane paneVin;
    private final Button editar;
    private final Button eliminar;

    private final ImageView imageEditar;
    private final ImageView imageEliminar;

    private final ObservableList<org.java.fase2final_manejo.models.Vehiculo> items;
    private final VehiculoService vehiculoService;

    public vehicleCell(ObservableList<org.java.fase2final_manejo.models.Vehiculo> items, VehiculoService vehiculoService) {
        super();
        this.vehiculoService = vehiculoService;
        this.items = items;

        Marca = new Text();
        Tipo = new Text();
        Linea = new Text();
        Modelo = new Text();
        Placa = new Text();
        Color = new Text();
        Motor = new Text();
        Chasis = new Text();
        Vin = new Text();

        paneMarca = new StackPane(Marca);
        paneTipo = new StackPane(Tipo);
        paneLinea = new StackPane(Linea);
        paneModelo = new StackPane(Modelo);
        panePlaca = new StackPane(Placa);
        paneColor = new StackPane(Color);
        paneMotor = new StackPane(Motor);
        paneChasis = new StackPane(Chasis);
        paneVin = new StackPane(Vin);

        paneMarca.setPrefSize(60, 30);
        paneMarca.setMaxWidth(60); // Limitar tamaño máximo
        Marca.setStyle("-fx-text-overrun: ellipsis;"); // Cortar texto con '...'

        paneTipo.setPrefSize(60, 30);
        paneTipo.setMaxWidth(60);
        Tipo.setStyle("-fx-text-overrun: ellipsis;");

        paneLinea.setPrefSize(60, 30);
        paneLinea.setMaxWidth(60);
        Linea.setStyle("-fx-text-overrun: ellipsis;");

        paneModelo.setPrefSize(60, 30);
        paneModelo.setMaxWidth(60);
        Modelo.setStyle("-fx-text-overrun: ellipsis;");

        panePlaca.setPrefSize(60, 30);
        panePlaca.setMaxWidth(60);
        Placa.setStyle("-fx-text-overrun: ellipsis;");

        paneColor.setPrefSize(60, 30);
        paneColor.setMaxWidth(60);
        Color.setStyle("-fx-text-overrun: ellipsis;");

        paneMotor.setPrefSize(60, 30);
        paneMotor.setMaxWidth(60);
        Motor.setStyle("-fx-text-overrun: ellipsis;");

        paneChasis.setPrefSize(60, 30);
        paneChasis.setMaxWidth(60);
        Chasis.setStyle("-fx-text-overrun: ellipsis;");

        paneVin.setPrefSize(60, 30);
        paneVin.setMaxWidth(60);
        Vin.setStyle("-fx-text-overrun: ellipsis;");

        imageEditar = new ImageView();
        imageEditar.setFitHeight(16);
        imageEditar.setFitWidth(16);
        imageEditar.setImage(new Image(new File("src/main/resources/org/java/fase2final_manejo/images/editar.png").toURI().toString()));
        editar = new Button("", imageEditar);
        editar.setStyle("-fx-background-color: #8cc1a1");
        editar.setPrefSize(24, 24);
        editar.setCursor(javafx.scene.Cursor.HAND);

        editar.setOnAction(event -> {
            Vehiculo vehiculo = getItem();
            if(vehiculo != null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fase2final_manejo/visuals/edits/editVehicleView.fxml"));
//                    loader.setControllerFactory(Main.context::getBean);
                    Parent root = loader.load();
                    editVehicleController controller = loader.getController();
                    controller.setVehiculo(vehiculo);

                    Stage emergente = new Stage();
                    emergente.initModality(Modality.APPLICATION_MODAL);
                    emergente.initStyle(StageStyle.UNDECORATED);
                    emergente.setTitle("Editar vehículo");
                    emergente.setScene(new Scene(root));
                    controller.setStage(emergente);
                    emergente.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        imageEliminar = new ImageView();
        imageEliminar.setFitHeight(16);
        imageEliminar.setFitWidth(16);
        imageEliminar.setImage(new Image(new File("src/main/resources/org/java/fase2final_manejo/images/basura.png").toURI().toString()));
        eliminar = new Button("", imageEliminar);
        eliminar.setStyle("-fx-background-color: red");
        eliminar.setPrefSize(24, 24);
        eliminar.setCursor(javafx.scene.Cursor.HAND);

        eliminar.setOnAction(event -> {
            BackupService backupService = new BackupService();
            Vehiculo item = getItem();
            if (item != null && mostrarMensajeConfirmacion("Eliminar vehiculo", "¿Estás seguro de eliminar el vehiculo de placa: " + item.getPlaca() + "?", "Esta acción no se puede deshacer")) {
                items.remove(item);
                vehiculoService.eliminarVehiculo(item.getId());
                backupService.generateBackupInBackground();
                mostrarMensajeExito();
            }
        });


        HBox hBox = new HBox(editar, eliminar);
        hBox.setSpacing(3);
        hBox.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);

        content = new HBox(paneMarca, paneTipo, paneLinea, paneModelo, paneColor, panePlaca, paneChasis, paneMotor, paneVin, hBox);
        content.setPrefSize(660, 30);
        content.setSpacing(20);
        content.setAlignment(javafx.geometry.Pos.CENTER);
    }
    @Override
    protected void updateItem(Vehiculo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            Marca.setText(item.getMarca().getNombre());
            Tipo.setText(item.getTipo().getNombre());
            Linea.setText(item.getLinea().getNombre());
            Modelo.setText(item.getModelo());
            Placa.setText(item.getNombre());
            Color.setText(item.getColor());
            Motor.setText(item.getMotor());
            Chasis.setText(item.getChasis());
            Vin.setText(item.getVin());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Vehiculo eliminado", "El vehiculo se ha eliminado correctamente");
    }
}
