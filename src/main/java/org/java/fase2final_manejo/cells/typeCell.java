package org.java.fase2final_manejo.cells;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.controllers.edits.editLineController;
import org.java.fase2final_manejo.controllers.edits.editTypeController;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Tipo;
import javafx.collections.ObservableList;
import org.java.fase2final_manejo.services.BackupService;
import org.java.fase2final_manejo.services.TipoService;
import java.io.File;
import java.io.IOException;


public class typeCell extends ListCell<Tipo> implements MensajesEmergentes {

    private final HBox content;
    private final Text nombre;
    private final Text año;
    private final StackPane paneNombre;
    private final StackPane paneAño;

    private final Button editar;
    private final Button eliminar;

    private final ImageView imageEditar;
    private final ImageView imageEliminar;

    private final ObservableList<Tipo> items;
    private final TipoService tipoService; // Lista de elementos

    // Constructor que recibe la lista de items
    public typeCell(ObservableList<Tipo> items, TipoService tipoService) {
        super();
        this.tipoService = tipoService;
        this.items = items;  // Referencia a la lista para poder eliminar elementos

        nombre = new Text();
        año = new Text();
        paneNombre = new StackPane(nombre);
        paneAño = new StackPane(año);

        paneNombre.setPrefSize(223, 30);
        paneAño.setPrefSize(223, 30);


        imageEditar = new ImageView();
        imageEditar.setFitHeight(16);
        imageEditar.setFitWidth(16);
        imageEditar.setImage(new Image(new File("src/main/resources/org/java/fx/images/editar.png").toURI().toString()));
        editar = new Button("", imageEditar);
        editar.setStyle("-fx-background-color: #8cc1a1");
        editar.setPrefSize(24, 24);
        editar.setCursor(javafx.scene.Cursor.HAND);

        editar.setOnAction(event -> {
            Tipo tipo = getItem();
            if(tipo != null){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/java/fx/visuals/edits/editTypeView.fxml"));
//                    loader.setControllerFactory(Main.context::getBean);
                    Parent root = loader.load();
                    editTypeController controller = loader.getController();
                    controller.setTipo(tipo);

                    Stage emergente = new Stage();
                    emergente.initModality(Modality.APPLICATION_MODAL);
                    emergente.initStyle(StageStyle.UNDECORATED);
                    emergente.setTitle("Editar tipo");
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
        imageEliminar.setImage(new Image(new File("src/main/resources/org/java/fx/images/basura.png").toURI().toString()));
        eliminar = new Button("", imageEliminar);
        eliminar.setStyle("-fx-background-color: red");
        eliminar.setPrefSize(24, 24);
        eliminar.setCursor(javafx.scene.Cursor.HAND);

        // Agregar el evento de eliminar
//        eliminar.setOnAction(event -> {
//            BackupService backupService = Main.context.getBean(BackupService.class);
//            Tipo item = getItem();  // Obtener el item de la celda actual
//            if (item != null && mostrarMensajeConfirmacion("Eliminar tipo", "¿Estás seguro de eliminar el tipo de nombre: " + item.getNombreTipo() + "?", "Esta acción no se puede deshacer")) {
//                tipoService.eliminarTipo(item.getId());  // Eliminar el item de la base de datos
//                items.remove(item);  // Eliminar el item de la lista
//                backupService.generateBackupInBackground();
//                mostrarMensajeExito();
//            }
//        });

        HBox hBox = new HBox(editar, eliminar);
        hBox.setPrefWidth(223);
        hBox.setSpacing(5);
        hBox.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);

        content = new HBox(paneNombre, paneAño, hBox);
        content.setPrefSize(670, 30);
        content.setSpacing(140);
        content.setPadding(new javafx.geometry.Insets(0, 30, 0, 30));
        content.setAlignment(javafx.geometry.Pos.CENTER);
    }

    @Override
    protected void updateItem(Tipo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            nombre.setText(item.getNombreTipo());
            año.setText(item.getAno().toString());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Tipo eliminado", "El tipo se ha eliminado correctamente");
    }
}