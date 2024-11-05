package org.java.fase2final_manejo.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.java.fase2final_manejo.services.LineaService;
import org.java.fase2final_manejo.services.MarcaService;
import org.java.fase2final_manejo.services.TipoService;
import org.java.fase2final_manejo.services.VehiculoService;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class homeController implements Initializable, MensajesEmergentes {
    @FXML
    private Label lbMarca;

    @FXML
    private Label lbLineas;

    @FXML
    private Label lbVehiculos;

    @FXML
    private Label lbTipos;

    private MarcaService marcaService;

    private LineaService lineaService;

    private VehiculoService vehiculoService;

    private TipoService tipoService;

    @FXML
    private Button btnExportar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        llenarLabels();
    }

    private void llenarLabels(){
        lbMarca.setText(String.valueOf(marcaService.contarMarcas()));
        lbLineas.setText(String.valueOf(lineaService.contarLineas()));
        lbVehiculos.setText(String.valueOf(vehiculoService.contarVehiculos()));
        lbTipos.setText(String.valueOf(tipoService.contarTipos()));
    }

    @FXML
    private void exportar() {
        // Crear el FileChooser
        FileChooser fileChooser = new FileChooser();

        // Configurar el título de la ventana
        fileChooser.setTitle("Selecciona dónde guardar el archivo");

        // Configurar la extensión del archivo (por ejemplo, JSON)
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        // Mostrar el diálogo de guardar archivo
        File fileToSave = fileChooser.showSaveDialog(new Stage());

        // Verificar si se seleccionó una ruta
        if (fileToSave != null) {
            // Ruta del archivo que ya tienes en tu proyecto (puedes cambiar esta ruta según sea necesario)

            File archivoExistente = new File("src/main/resources/org/java/fase2final_manejo/backup/backup_data.json");

            // Copiar el archivo existente a la nueva ubicación seleccionada
            try {
                Files.copy(archivoExistente.toPath(), fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
                mostrarMensajeConfirmacion("Archivo exportado correctamente.", "El archivo se exportó correctamente.", "El archivo se exporto correctamente a la ruta");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al exportar el archivo.");
            }
        } else {
            System.out.println("No se seleccionó ninguna ubicación para guardar.");
        }
    }
}
