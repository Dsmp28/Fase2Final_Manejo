package org.java.fase2final_manejo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.java.fase2final_manejo.Main;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.repositories.VehiculoRepository;
import org.java.fase2final_manejo.services.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class newLineController implements Initializable, MensajesEmergentes {
    @FXML
    private TextField txtLinea;
    @FXML
    private TextField txtAnio;
    @FXML
    private ComboBox<Marca> cbMarca;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnCerrar;

    private Stage stage;

    private MarcaService marcaService;

    private LineaService lineaService;

    private BackupService backupService;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @FXML
    private void agregarNuevo() {
        try {
            Marca marca = cbMarca.getValue();
            String nombreLinea = txtLinea.getText().trim();
            Integer anio = Integer.parseInt(txtAnio.getText());

            if (marca == null || nombreLinea.isEmpty()) {
                throw new Exception("Todos los campos son obligatorios");
            }

            // Verificar si ya existe una línea con el mismo nombre y diferente ID
            List<Linea> lineasExistentes = lineaService.buscarLineaPorNombre(txtLinea.getText());
            for (Linea lineaExistente : lineasExistentes) {
                if (lineaExistente.getNombreLinea().equalsIgnoreCase(txtLinea.getText())) {
                    mostrarMensajeError("Ya existe una línea con el nombre ingresado.");
                    return;
                }
            }

            Linea nuevaLinea = new Linea(marca, nombreLinea, anio);
            lineaService.guardarLinea(nuevaLinea);
            backupService.generateBackup();
            mostrarMensajeExito();
            cerrar();
        } catch (Exception e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backupService = new BackupService();
        String dataMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataMarca.json";
        String indexMarcaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexMarca.txt";
        String dataLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/dataLinea.json";
        String indexLineaPath = "src/main/resources/org/java/fase2final_manejo/Data/indexLinea.txt";

        marcaService = new MarcaService(new MarcaRepository(dataMarcaPath, indexMarcaPath));
        lineaService = new LineaService(new LineaRepository(dataLineaPath, indexLineaPath));
        cargarMarcas();
    }

    private void cargarMarcas(){
        cbMarca.getItems().addAll(marcaService.obtenerTodasLasMarcas());
    }


    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo guardar la línea, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al guardar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Linea guardada", "La linea se guardo correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage = emergente;
    }
}
