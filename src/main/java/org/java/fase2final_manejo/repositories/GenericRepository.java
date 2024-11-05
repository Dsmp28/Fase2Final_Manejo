package org.java.fase2final_manejo.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

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
        File file = new File(dataFilePath);
        List<T> entities = new ArrayList<>();

        if (file.exists()) {
            try {
                entities = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
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
    private void saveIndex(List<T> entities) {
        List<String> indexEntries = new ArrayList<>();
        int currentIndex = 0;

        for (T entity : entities) {
            try {
                // Convertir el objeto a JSON para calcular su longitud
                String jsonString = objectMapper.writeValueAsString(entity);
                int length = jsonString.getBytes().length;

                // Crear el índice del objeto: "nombre del objeto, índice, longitud"
                String name = entity.toString(); // Asumiendo que el método toString() devuelve el nombre del objeto
                String indexEntry = name + ", " + currentIndex + ", " + length;
                indexEntries.add(indexEntry);

                // Actualizar el índice para el próximo objeto
                currentIndex += length + 1; // Sumando 1 por el salto de línea entre objetos

            } catch (IOException e) {
                System.out.println("Error al procesar el objeto para el índice: " + e.getMessage());
            }
        }

        // Ordenar los índices alfabéticamente por el nombre del objeto
        indexEntries.sort(Comparator.comparing(entry -> entry.split(", ")[0]));

        // Escribir los índices en el archivo indexFilePath
        try (BufferedWriter writer = Files.newBufferedWriter(new File(indexFilePath).toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
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

    public Optional<T> findById(Long id){
        return Optional.empty();
    }
}

