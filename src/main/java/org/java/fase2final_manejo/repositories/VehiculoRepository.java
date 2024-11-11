package org.java.fase2final_manejo.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.java.fase2final_manejo.models.IndexEntry;
import org.java.fase2final_manejo.models.Vehiculo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class VehiculoRepository extends GenericRepository<Vehiculo> {
    List<String> VINs = new ArrayList<>();
    Map<String, IndexEntry> mapVINEntry = new HashMap<>();
    Map<String, Integer> indexMapVINStart = new HashMap<>();
    Map<String, Integer> indexMapVINNext = new HashMap<>();
    private String dataFilePath;
    private String indexFilePath;
    private ObjectMapper objectMapper = new ObjectMapper();

    int digitosOriginales = 1;

    public VehiculoRepository(String dataFilePath, String indexFilePath) {
        super(dataFilePath, indexFilePath, Vehiculo.class);
        this.dataFilePath = dataFilePath;
        this.indexFilePath = indexFilePath;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<Vehiculo> findByPlacaContainingIgnoreCase(String placa) {
        return searchByName(placa);
    }

    private String getName(Vehiculo entity) {
        try {
            // Obtiene el VIN por medio del método getVIN
            return (String) entity.getClass().getMethod("getVin").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el nombre del objeto", e);
        }
    }
    @Override
    public void saveIndex(List<Vehiculo> entities){
        List<String> indexEntries = new ArrayList<>();
        int currentIndex = 2; // Inicia en 2 para considerar el inicio del array '[\n'
        int currentIndexMapStart = 2;
        int currentIndexMapNext = 2;
        for (int i = 0; i < entities.size(); i++) {
            try {

                // Convertir el objeto a JSON
                String jsonString = objectMapper.writeValueAsString(entities.get(i));

                // Agregar delimitadores solo si no es el último objeto
                if (i < entities.size() - 1) {
                    jsonString += ",\n";
                } else {
                    jsonString += "\n";
                }

                // Calcular la longitud en bytes del JSON con delimitadores
                byte[] jsonData = jsonString.getBytes(StandardCharsets.UTF_8);
                int length = jsonData.length;

                // Crear la entrada del índice con el nombre del objeto, índice y longitud
                String vin = getName(entities.get(i)); // Usa un método getName() para obtener el nombre (placa)
                VINs.add(vin); // Agregar el nombre a la lista de nombres
                VINs.sort(Comparator.naturalOrder()); // Ordenar la lista de nombres

                String indexEntry;
                //Verifica si el vin actual es el ultimo o primero (buscar en la lista de VINs)
                if(VINs.indexOf(vin) == VINs.size()-1 || VINs.indexOf(vin) == 0){ //Si es el último o el primero
                    IndexEntry indexEntry1 = new IndexEntry(vin, currentIndex, length, -1);
                    mapVINEntry.put(vin, indexEntry1);
                    indexEntry = vin + "," + currentIndex + "," + length + ",-1"; //Si es el último o el primero, el next es -1
                }else{
                    IndexEntry indexEntry1 = new IndexEntry(vin, currentIndex, length, currentIndexMapNext);
                    mapVINEntry.put(vin, indexEntry1);
                    indexEntry = vin + "," + currentIndex + "," + length + "," + currentIndexMapNext; //Si no es el último o el primero, el next es el siguiente
                }

                //Agregar al map de inicio
                indexMapVINStart.put(vin, currentIndexMapStart); //Agregar al map de inicio
                if(!VINs.isEmpty()){ //Si el VIN no está vacío
                    //Obtener el VIN menor
                    String VINMenor = VINs.get(0);
                    int inicio = indexMapVINStart.get(VINMenor);
                    //Obtener dígitos de la posición de inicio
                    int digitos = String.valueOf(inicio).length();
                    //Si los dígitos de la posición de inicio son mayores a los 'originales'
                    if(digitos > digitosOriginales){
                        digitosOriginales = digitos; //Actualizar los dígitos originales
                        int digitosASumar = digitosOriginales - 1; //Por cada digito extra de los originales
                        while(digitosASumar > 0){
                            //Sumar 1 a los indices de inicio y next de TODOS en el hashmap
                            for(String key : indexMapVINStart.keySet()){
                                int valor = indexMapVINStart.get(key);
                                valor++;
                                indexMapVINStart.put(key, valor);
                            }
                            for(String key : indexMapVINNext.keySet()){
                                int valor = indexMapVINNext.get(key);
                                valor++;
                                indexMapVINNext.put(key, valor);
                            }
                            //Restar 1 a los digitos a sumar
                            digitosASumar--;
                        }
                    }
                }

                //Actualizar el índice para el próximo objeto
                currentIndexMapStart += indexEntry.length();
                //Actualizar el índice para el próximo objeto
                currentIndexMapNext += indexEntry.length();
                indexMapVINNext.put(vin, currentIndexMapNext); //Agregar al map de next

                //Agregar la entrada del índice a la lista
                indexEntries.add(indexEntry);

                //Actualizar el nextByteOffset de los anteriores
                //Si hay más de 1 elemento
                if(VINs.size() > 1){
                    //Iterar entre todos menos el último
                    for(int j = 0; j < VINs.size() - 1; j++){
                        IndexEntry previousEntry = mapVINEntry.get(VINs.get(j)); //Obtener la entrada anterior (el VIN anterior)
                        String previousVin = previousEntry.getVin(); //Obtener el previo VIN
                        String nextVin = VINs.get(j+1); //Obtener el siguiente VIN
                        int newNextonIndex = indexMapVINStart.get(nextVin); //Obtener la posición inicial del VIN siguiente
                        previousEntry.setNextByteOffset(newNextonIndex); //Actualizar el nextByteOffset del anterior
                        mapVINEntry.put(previousVin, previousEntry); //Actualizar el map de VINs
                        for(int k = 0; k < indexEntries.size(); k++){ //Buscar en el indexEntries el VIN anterior para actualizarlo
                            String[] entry = indexEntries.get(k).split(","); //Dividir por comas
                            if(entry[0].equals(previousVin)){ //Si el VIN es igual al anterior
                                indexEntries.set(k, previousVin + "," + entry[1] + "," + entry[2] + "," + newNextonIndex); //Actualizar el indexEntry
                            }
                        }
                    }
                }

                // Actualizar el índice para el próximo objeto
                currentIndex += length;
            } catch (IOException e) {
                System.out.println("Error al procesar el objeto para el índice: " + e.getMessage());
            }
        }

        //Ya está definida la lista de indexEntries y de VINs, ahora debo encontrar la posición en bytes del primer registro en el archivo de datos
        String VINMenor = VINs.get(0);
        //Obtener el IndexMapStart del menor
        int inicio = indexMapVINStart.get(VINMenor);
        //Debug
        System.out.println("VINs: " + VINs);
        System.out.println("VINMenor: " + VINMenor);
        //Debug
        System.out.println("IndexEntries: " + indexEntries);
        System.out.println("IndexMapStart: " + indexMapVINStart);
        System.out.println("IndexMapNext: " + indexMapVINNext);
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(indexFilePath), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(String.valueOf(inicio));
            writer.newLine();
            for (String entry : indexEntries) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de índice: " + e.getMessage());
        }
    }
}

