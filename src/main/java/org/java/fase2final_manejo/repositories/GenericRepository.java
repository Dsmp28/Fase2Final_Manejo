package org.java.fase2final_manejo.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericRepository<T> {

    private final String dataFilePath;
    private final String indexFilePath;
    private final Class<T> clazz;
    private final ObjectMapper objectMapper;

    public GenericRepository(String dataFilePath, String indexFilePath, Class<T> clazz) {
        this.dataFilePath = dataFilePath;
        this.indexFilePath = indexFilePath;
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print
    }

    public void save(T entity) {
        List<T> entities = findAll(); // Cargar todos los datos existentes
        boolean updated = false;

        // Verificar si el objeto ya existe en la lista para actualizarlo
        for (int i = 0; i < entities.size(); i++) {
            T existingEntity = entities.get(i);
            if (getId(existingEntity).equals(getId(entity))) { // Suponiendo que el atributo 'id' es único
                entities.set(i, entity); // Reemplazar el objeto existente
                updated = true;
                break;
            }
        }

        // Si el objeto no existía, agregarlo a la lista
        if (!updated) {
            entities.add(entity);
        }

        // Guardar la lista completa de objetos en formato JSON
        try {
            objectMapper.writeValue(new File(dataFilePath), entities);
            saveIndex(entities); // Actualizar el archivo de índice
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        File file = new File(dataFilePath);

        if (file.exists()) {
            try {
                entities = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON en " + dataFilePath + ": " + e.getMessage());
            }
        } else {
            System.out.println("El archivo no existe en la ruta: " + dataFilePath);
        }

        return entities;
    }


    private Object getId(T entity) {
        try {
            return entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el ID del objeto", e);
        }
    }

    // Método para guardar el índice
    public void saveIndex(List<T> entities) {
        List<String> indexEntries = new ArrayList<>();
        int currentIndex = 2; // Inicia en 2 para considerar el inicio del array '[\n'

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
                String name = getName(entities.get(i)); // Usa un método getName() para obtener el nombre
                String indexEntry = name + ", " + currentIndex + ", " + length;
                indexEntries.add(indexEntry);

                // Actualizar el índice para el próximo objeto
                currentIndex += length;
            } catch (IOException e) {
                System.out.println("Error al procesar el objeto para el índice: " + e.getMessage());
            }
        }

        // Escribir el índice en el archivo, ordenado por nombre
        indexEntries.sort(Comparator.comparing(entry -> entry.split(", ")[0]));
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(indexFilePath), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String entry : indexEntries) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de índice: " + e.getMessage());
        }
    }


    public void deleteById(Long id){

    }

    public long count(){
        return findAll().size();
    }

    public List<T> searchByName(String name) {
        List<T> results = new ArrayList<>();
        Path filePath = Path.of(dataFilePath);

        // Leer el archivo de índices y buscar coincidencias de nombre
        try (BufferedReader reader = new BufferedReader(new FileReader(indexFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length != 3) continue;

                String objectName = parts[0];
                int index = Integer.parseInt(parts[1]);
                int length = Integer.parseInt(parts[2]);

                // Si el nombre en el índice coincide con el parámetro de búsqueda
                if (objectName.toLowerCase().contains(name.toLowerCase())) {
                    // Extraer el fragmento JSON específico desde el archivo usando el índice y la longitud
                    T object = loadObjectFromJson(index, length);
                    if (object != null) {
                        results.add(object);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de índices: " + e.getMessage());
        }

        return results;
    }

    private T loadObjectFromJson(int index, int length) {
        try (RandomAccessFile file = new RandomAccessFile(dataFilePath, "r")) {
            file.seek(index);
            byte[] data = new byte[length];
            file.readFully(data);

            String json = new String(data, StandardCharsets.UTF_8);
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            System.out.println("Error al cargar el objeto desde el archivo JSON: " + e.getMessage());
            return null;
        }
    }

    private String getName(T entity) {
        try {
            // Asume que hay un método `getNombre` en la clase T que devuelve el nombre
            return (String) entity.getClass().getMethod("getNombre").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el nombre del objeto", e);
        }
    }


}

