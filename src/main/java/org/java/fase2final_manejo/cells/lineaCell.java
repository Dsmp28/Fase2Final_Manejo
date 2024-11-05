package org.java.fase2final_manejo.cells;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.fase2final_manejo.controllers.edits.editLineController;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.services.LineaService;
import java.io.File;
import java.io.IOException;

public class lineaCell extends ListCell<Linea> implements MensajesEmergentes {
    private final HBox content;
    private final Text nombre;
    private final Text año;
    private final Text marca;
    private final Button editar;
    private final Button eliminar;

    private final StackPane paneNombre;
    private final StackPane paneAño;
    private final StackPane paneMarca;

    private final ImageView imageEditar;
    private final ImageView imageEliminar;

    private final ObservableList<Linea> items;
    private final LineaService lineaService;

    public lineaCell(ObservableList<Linea> items, LineaService lineaService) {
        super();
        this.items = items;
        this.lineaService = lineaService;

        marca = new Text();
        nombre = new Text();
        año = new Text();
        paneNombre = new StackPane(nombre);
        paneAño = new StackPane(año);
        paneMarca = new StackPane(marca);
        paneMarca.setPrefSize(167, 30);
        paneNombre.setPrefSize(167, 30);
        paneAño.setPrefSize(167, 30);

        imageEditar = new ImageView();
        imageEditar.setFitHeight(16);
        imageEditar.setFitWidth(16);
        imageEditar.setImage(new Image(new File("src/main/resources/org/java/fase2final_manejo/images/editar.png").toURI().toString()));
        editar = new Button("", imageEditar);
        editar.setStyle("-fx-background-color: #8cc1a1");
        editar.setPrefSize(24, 24);
        editar.setCursor(javafx.scene.Cursor.HAND);

        editar.setOnAction(event -> {
            Linea linea = getItem();
            if(linea != null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fase2final_manejo/visuals/edits/editLineView.fxml"));
                    //loader.setControllerFactory(Main.context::getBean);
                    Parent root = loader.load();
                    editLineController controller = loader.getController();
                    controller.setLinea(linea);

                    Stage emergente = new Stage();
                    emergente.initModality(Modality.APPLICATION_MODAL);
                    emergente.initStyle(StageStyle.UNDECORATED);
                    emergente.setTitle("Editar Linea");
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

        // Agregar el evento de eliminar
        eliminar.setOnAction(event -> {
            //BackupService backupService = Main.context.getBean(BackupService.class);
            Linea item = getItem();
            if (item != null && mostrarMensajeConfirmacion("Eliminar línea", "¿Estás seguro de eliminar la línea: " + item.getNombre() + "?", "Esta acción no se puede deshacer")) {
                items.remove(item);
                lineaService.eliminarLinea(item.getId());
//                try {
//                    backupService.generateBackup();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                mostrarMensajeExito();
            }
        });

        HBox hBox = new HBox(editar, eliminar);
        hBox.setSpacing(5);
        hBox.setAlignment(javafx.geometry.Pos.CENTER);

        content = new HBox(paneMarca, paneNombre, paneAño, hBox);
        content.setPrefSize(660, 30);
        content.setSpacing(140);
        content.setPadding(new javafx.geometry.Insets(0, 0, 0, 10));
        content.setAlignment(javafx.geometry.Pos.CENTER);
    }

    @Override
    protected void updateItem(Linea item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            nombre.setText(item.getNombre());
            año.setText(item.getAno().toString());
            marca.setText(item.getMarca().getNombre());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }


    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Linea eliminada", "La linea se ha eliminado correctamente");
    }
}

