package org.java.fase2final_manejo.controllers.edits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.java.fase2final_manejo.controllers.MensajesEmergentes;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.repositories.VehiculoRepository;
import org.java.fase2final_manejo.services.*;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class editVehicleController implements Initializable, MensajesEmergentes {
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtChasis;
    @FXML
    private TextField txtMotor;
    @FXML
    private TextField txtVin;
    @FXML
    private ComboBox<Marca> cbMarca;
    @FXML
    private ComboBox<Tipo> cbTipo;
    @FXML
    private ComboBox<Linea> cbLinea;
    @FXML
    private Button btnAñadir;
    @FXML
    private Button btnCerrar;
    @FXML
    private TextField txtNumAsientos;
    private Stage stage;

    private Vehiculo vehiculo;

    private MarcaService marcaService;

    private LineaService lineaService;

    private TipoService tipoService;

    private VehiculoService vehiculoService;

    private BackupService backupService;
    private org.java.fase2final_manejo.controllers.vehicleController vehicleController;

    @FXML
    private void cerrar(){
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehicleController = new org.java.fase2final_manejo.controllers.vehicleController();
        backupService = new BackupService();
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
    }
    @FXML
    private void editarVehiculo(){
        try{
            if (txtModelo.getText().isEmpty() || txtColor.getText().isEmpty() || txtPlaca.getText().isEmpty() || txtChasis.getText().isEmpty() || txtMotor.getText().isEmpty() || txtVin.getText().isEmpty() || txtNumAsientos.getText().isEmpty() || cbMarca.getValue() == null || cbTipo.getValue() == null || cbLinea.getValue() == null){
                mostrarMensajeError("Todos los campos son obligatorios");
                return;
            }
            // Verificar si ya existe un vehículo con los mismos valores
            List<Vehiculo> vehiculosTotales = vehiculoService.obtenerTodoslosVehiculos();
            for (Vehiculo vehiculo : vehiculosTotales) {
                if (vehiculo.getPlaca().equalsIgnoreCase(txtPlaca.getText()) || vehiculo.getChasis().equalsIgnoreCase(txtChasis.getText()) || vehiculo.getMotor().equalsIgnoreCase(txtMotor.getText()) || vehiculo.getVin().equalsIgnoreCase(txtVin.getText()) && !vehiculo.getId().equals(this.vehiculo.getId())) {
                    mostrarMensajeError("Ya existe un vehículo con la placa ingresada.");
                    return;
                }
            }
            vehiculo.setModelo(txtModelo.getText());
            vehiculo.setColor(txtColor.getText());
            vehiculo.setPlaca(txtPlaca.getText());
            vehiculo.setChasis(txtChasis.getText());
            vehiculo.setMotor(txtMotor.getText());
            vehiculo.setVin(txtVin.getText());
            vehiculo.setAsientos(Integer.parseInt(txtNumAsientos.getText()));
            vehiculo.setMarca(cbMarca.getValue());
            vehiculo.setTipo(cbTipo.getValue());
            vehiculoService.guardarVehiculo(vehiculo);
            backupService.generateBackupInBackground(); //Esto genera el backup. Al hacer el editar agregarlo antes de mostrar el mensaje de que se edito con exito
            vehicleController.cargarVehiculos();
            mostrarMensajeExito();
            cerrar();
        }catch (Exception e){
            mostrarMensajeError("Revise que los campos no sean repetidos con otros valores existentes");
        }
    }

    public void setVehiculo(Vehiculo vehiculo){
        cargarMarcas();
        cargarTipos();
        this.vehiculo = vehiculo;
        llenarCampos();
    }
    private void llenarCampos(){
        txtModelo.setText(vehiculo.getModelo());
        txtColor.setText(vehiculo.getColor());
        txtPlaca.setText(vehiculo.getNombre());
        txtChasis.setText(vehiculo.getChasis());
        txtMotor.setText(vehiculo.getMotor());
        txtVin.setText(vehiculo.getVin());
        txtNumAsientos.setText(String.valueOf(vehiculo.getAsientos()));
        cbMarca.setValue(vehiculo.getMarca());
        cbTipo.setValue(vehiculo.getTipo());
        cbLinea.setValue(vehiculo.getLinea());
        cargarLineas();
    }
    private void cargarMarcas(){
        cbMarca.getItems().addAll(marcaService.obtenerTodasLasMarcas());
    }

    public void cargarTipos(){
        cbTipo.getItems().addAll(tipoService.obtenerTodoslosTipos());
    }

    @FXML
    private void cargarLineas() {
        // Asegúrate de que hay una marca seleccionada antes de cargar las líneas
        if (cbMarca.getValue() == null) {
            cbLinea.getItems().clear(); // Limpia las líneas si no hay marca seleccionada
            return; // Sal del método si no hay marca seleccionada
        }

        cbLinea.getItems().clear();
        List<Linea> lineas = lineaService.obtenerLineaPorMarca(cbMarca.getValue().getId())
                .stream()
                .filter(linea -> linea != null)  // Filtra los elementos nulos
                .collect(Collectors.toList());
        cbLinea.getItems().addAll(lineas);

        // Configurar el ComboBox para mostrar solo el nombre de la línea
        cbLinea.setConverter(new StringConverter<>() {
            @Override
            public String toString(Linea linea) {
                return (linea != null) ? linea.getNombreLinea() : ""; // Asegura que linea no sea null
            }

            @Override
            public Linea fromString(String string) {
                return cbLinea.getItems().stream()
                        .filter(linea -> linea != null && linea.getNombreLinea().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }




    private void mostrarMensajeError(String mensaje){
        String mensajeFormateado = String.format("No se pudo guardar el vehiculo, por favor revise los datos\nError: %s", mensaje);

        mostrarMensajeEmergente(Alert.AlertType.ERROR, "Error", "Error al guardar", mensajeFormateado);
    }

    private void mostrarMensajeExito(){
        mostrarMensajeEmergente(Alert.AlertType.INFORMATION, "Exito", "Vehiculo guardado", "El vehiculo se guardo correctamente");
    }

    public void setStage(Stage emergente) {
        this.stage= emergente;
    }
}