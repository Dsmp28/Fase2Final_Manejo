package org.java.fase2final_manejo.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.concurrent.Task;
import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.repositories.LineaRepository;
import org.java.fase2final_manejo.repositories.MarcaRepository;
import org.java.fase2final_manejo.repositories.TipoRepository;
import org.java.fase2final_manejo.repositories.VehiculoRepository;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackupService {

    private VehiculoService vehiculoService;

    private LineaService lineaService;

    private MarcaService marcaService;

    private TipoService tipoService;

    private final File backupFile = new File("src/main/resources/org/java/fase2final_manejo/backup/backup_data.json");
    private final ObjectMapper objectMapper;

    public BackupService() {
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

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    //Método para ejecutar el backup en segundo plano
    public void generateBackupInBackground() {
        // Crear el Task para ejecutar el backup en segundo plano
        Task<Void> backupTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                generateBackup();
                return null;
            }
        };

        // Ejecutar el Task en un nuevo hilo
        new Thread(backupTask).start();
    }

    public void generateBackup() throws IOException {
        // Crear un nuevo mapa de datos para el backup
        Map<String, Object> backupData = new HashMap<>();

        // Obtener los datos actuales de la base de datos
        List<Marca> marcas = marcaService.obtenerTodasLasMarcas();
        List<Tipo> tipos = tipoService.obtenerTodoslosTipos();
        List<Linea> lineas = lineaService.obtenerTodoslasLineas();
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodoslosVehiculos();

        // Asignar los nuevos datos al mapa
        backupData.put("vehiculos", vehiculos);
        backupData.put("lineas", lineas);
        backupData.put("marcas", marcas);
        backupData.put("tipos", tipos);

        // Guardar el archivo JSON actualizado (reemplazando el contenido)
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(backupFile, backupData);

        System.out.println("Backup generado en: " + backupFile.getAbsolutePath());
    }
}
