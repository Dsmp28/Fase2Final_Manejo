package org.java.fase2final_manejo.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.repositories.VehiculoRepository;
import org.java.fase2final_manejo.services.LineaService;
import org.java.fase2final_manejo.services.MarcaService;
import org.java.fase2final_manejo.services.TipoService;
import org.java.fase2final_manejo.services.VehiculoService;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        String dataMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json";
        String indexMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt";
        String dataLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataLinea.json";
        String indexLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexLinea.txt";
        String dataVehiculoPath = "src/main/resources/org/java/fase2final_manejo/Data/dataVehiculo.json";
        String indexVehiculoPath = "src/main/resources/org/java/fase2final_manejo/Data/indexVehiculo.txt";
        String dataTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/dataTipo.json";
        String indexTipoPath = "src/main/resources/org/java/fase2final_manejo/Data/indexTipo.txt";

        marcaService = new MarcaService(new MarcaRepository(dataMarcaPath, indexMarcaPath));
        lineaService = new LineaService(new LineaRepository(dataLineaPath, indexLineaPath));
        vehiculoService = new VehiculoService(new VehiculoRepository(dataVehiculoPath, indexVehiculoPath));
        tipoService = new TipoService(new TipoRepository(dataTipoPath, indexTipoPath));

        Linea.contadorId = lineaService.contarLineas();
        Marca.contadorId = marcaService.contarMarcas();
        Tipo.contadorId = tipoService.contarTipos();
        Vehiculo.contadorId = vehiculoService.contarVehiculos();
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
        // Crear el FileChooser para seleccionar la ubicación de la carpeta
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecciona dónde guardar la carpeta con archivos CSV");

        // Mostrar el diálogo de selección de carpeta
        File directoryToSave = directoryChooser.showDialog(new Stage());

        // Verificar si se seleccionó una ubicación
        if (directoryToSave != null) {
            // Crear la carpeta donde se guardarán los archivos CSV
            File exportFolder = new File(directoryToSave, "ExportedCSVs");
            if (!exportFolder.exists()) {
                exportFolder.mkdir();
            }

            // Ruta del archivo JSON en tu proyecto
            File archivoExistente = new File("src/main/resources/org/java/fase2final_manejo/backup/backup_data.json");

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(archivoExistente);

                // Recorrer cada lista en el JSON y escribirla en archivos CSV individuales
                for (Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ) {
                    Map.Entry<String, JsonNode> entry = it.next();
                    String sectionName = entry.getKey(); // Nombre de la sección (lineas, marcas, etc.)
                    JsonNode arrayNode = entry.getValue();

                    if (arrayNode.isArray() && arrayNode.size() > 0) {
                        // Crear un archivo CSV para cada sección
                        File csvFile = new File(exportFolder, sectionName + ".csv");
                        try (FileWriter csvWriter = new FileWriter(csvFile)) {
                            // Escribir la fila de encabezado con los nombres de los atributos
                            JsonNode firstItem = arrayNode.get(0);
                            if (firstItem != null && firstItem.isObject()) {
                                Iterator<String> fieldNames = firstItem.fieldNames();
                                while (fieldNames.hasNext()) {
                                    String fieldName = fieldNames.next();
                                    csvWriter.append(fieldName).append(",");
                                }
                                csvWriter.append("\n");
                            }

                            // Escribir cada objeto de la lista como una fila en el CSV
                            for (JsonNode item : arrayNode) {
                                Iterator<String> fieldNames = item.fieldNames();
                                while (fieldNames.hasNext()) {
                                    String fieldName = fieldNames.next();
                                    JsonNode value = item.get(fieldName);

                                    // Comprobar si el campo es un objeto anidado que tiene un nombre
                                    if (value.isObject() && value.has("nombre")) {
                                        csvWriter.append(value.get("nombre").asText());
                                    }else if ((value.isObject() && value.has("nombreTipo"))){
                                        csvWriter.append(value.get("nombreTipo").asText());
                                    } else if (value.isObject() && value.has("nombreLinea")){
                                        csvWriter.append(value.get("nombreLinea").asText());
                                    }else if (value.isTextual()) {
                                        csvWriter.append(value.asText());
                                    } else if (value.isInt()) {
                                        csvWriter.append(String.valueOf(value.asInt()));
                                    } else if (value.isDouble()) {
                                        csvWriter.append(String.valueOf(value.asDouble()));
                                    } else if (value.isArray() && fieldName.equals("fechaCreacion")) {
                                        // Formato específico para fechas en LocalDate [YYYY, MM, DD]
                                        String formattedDate = value.get(0).asInt() + "-" + value.get(1).asInt() + "-" + value.get(2).asInt();
                                        csvWriter.append(formattedDate);
                                    } else if (value.isArray()) {
                                        csvWriter.append(value.toString()); // Para otros arrays
                                    } else {
                                        csvWriter.append(value.asText()); // Cualquier otro tipo se convierte a texto
                                    }
                                    csvWriter.append(",");
                                }
                                csvWriter.append("\n");
                            }
                        }
                    }
                }

                mostrarMensajeConfirmacion("Exportación completada", "Los archivos CSV se exportaron correctamente en la carpeta seleccionada.", "Archivos exportados a la carpeta: " + exportFolder.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al exportar los archivos CSV.");
            }
        } else {
            System.out.println("No se seleccionó ninguna ubicación para guardar.");
        }
    }




}
