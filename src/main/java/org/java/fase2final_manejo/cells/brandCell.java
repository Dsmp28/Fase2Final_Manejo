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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.java.fase2final_manejo.Main;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.controllers.edits.editBrandController;
import org.java.fase2final_manejo.controllers.newBrandController;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.MarcaService;
import org.java.fase2final_manejo.services.TipoService;

import java.io.File;
import java.io.IOException;


public class brandCell extends ListCell<Marca> implements MensajesEmergentes {
    private final HBox content;
    private final Text nombre;
    private final Text año;
    private final Text fundador;

    private final StackPane paneNombre;
    private final StackPane paneAño;
    private final StackPane paneFundador;
    private final Button editar;
    private final Button eliminar;

    private final ImageView imageEditar;
    private final ImageView imageEliminar;

    private final ObservableList<Marca> items;
    private final MarcaService marcaService;

    public brandCell(ObservableList<Marca> items, MarcaService tipoService) {
        super();
        this.marcaService = tipoService;
        this.items = items;

        fundador = new Text();
        nombre = new Text();
        año = new Text();

        paneNombre = new StackPane(nombre);
        paneAño = new StackPane(año);
        paneFundador = new StackPane(fundador);

        paneFundador.setPrefSize(160, 30);
        paneNombre.setPrefSize(160, 30);
        paneAño.setPrefSize(160, 30);

        imageEditar = new ImageView();
        imageEditar.setFitHeight(16);
        imageEditar.setFitWidth(16);
        imageEditar.setImage(new Image(new File("src/main/resources/org/java/fx/images/editar.png").toURI().toString()));

        editar = new Button("", imageEditar);
        editar.setStyle("-fx-background-color: #8cc1a1");
        editar.setPrefSize(24, 24);
        editar.setCursor(javafx.scene.Cursor.HAND);

        editar.setOnAction(event -> {
            Marca marca = getItem();
            if (marca != null) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fx/visuals/edits/editBrandView.fxml"));
                    //loader.setControllerFactory(Main.context::getBean);
                    Parent root = loader.load();
                    editBrandController controller = loader.getController();
                    controller.setMarca(marca);

                    Stage emergente = new Stage();
                    emergente.initModality(Modality.APPLICATION_MODAL);
                    emergente.initStyle(StageStyle.UNDECORATED);
                    emergente.setResizable(false);
                    emergente.setMaximized(false);
                    emergente.setTitle("Editar una marca");
                    emergente.setScene(new Scene(root));
                    controller.setStage(emergente);
                    emergente.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imageEliminar = new ImageView();
        imageEliminar.setFitHeight(16);
        imageEliminar.setFitWidth(16);
        imageEliminar.setImage(new Image(new File("src/main/resources/org/java/fx/images/basura.png").toURI().toString()));
        eliminar = new Button("", imageEliminar);
        eliminar.setStyle("-fx-background-color: red");
        eliminar.setPrefSize(24, 24);
        eliminar.setCursor(javafx.scene.Cursor.HAND);

        eliminar.setOnAction(event -> {
            //BackupService backupService = Main.context.getBean(BackupService.class);
            Marca item = getItem();
            if (item != null && mostrarMensajeConfirmacion("Eliminar marca", "¿Estás seguro de eliminar la marca de nombre: " + item.getNombre() + "?", "Esta acción no se puede deshacer")) {
                items.remove(item);
                marcaService.eliminarMarca(item.getId());
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
        hBox.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);
        hBox.setPrefWidth(120);


        content = new HBox(paneNombre, paneAño, paneFundador, hBox);
        content.setPrefSize(620, 30);
        content.setSpacing(130);
        content.setAlignment(javafx.geometry.Pos.CENTER);
        content.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
    }

    @Override
    protected void updateItem(Marca item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            nombre.setText(item.getNombre());
            año.setText(item.getFechaCreacion().toString());
            fundador.setText(item.getFundador());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Marca eliminada", "La marca se ha eliminado correctamente");
    }
}
