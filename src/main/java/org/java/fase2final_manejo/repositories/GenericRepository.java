package org.java.fase2final_manejo.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepository<T> {

    private String dataFilePath;
    private String indexFilePath;
    private Class<T> clazz;
    private final ObjectMapper objectMapper;

    public GenericRepository(String dataFilePath, String indexFilePath, Class<T> clazz) {
        this.dataFilePath = dataFilePath;
        this.indexFilePath = indexFilePath;
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        File file = new File(dataFilePath);

        if (file.exists()) {
            try {
                entities = objectMapper.readValue(file, new TypeReference<List<T>>() {});
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }

        return entities;
    }

    public void save(T entity) {
        List<T> entities = new ArrayList<>();
        File file = new File(dataFilePath);

        // Leer los valores actuales en el archivo JSON, si existe
        if (file.exists()) {
            try {
                T[] existingEntities = objectMapper.readValue(file, (Class<T[]>) entity.getClass().arrayType());
                if (existingEntities != null) {
                    Collections.addAll(entities, existingEntities);
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }

        // Verificar si el objeto ya existe en la lista para actualizarlo
        boolean updated = false;
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
            objectMapper.writeValue(file, entities);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private Object getId(T entity) {
        // Implementación de obtención del 'id' del objeto.
        // Se puede usar reflexión o asegurarse de que el objeto tenga un método getId().
        try {
            return entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el ID del objeto", e);
        }
    }

    public void deleteById(Long id){

    }

    public long count(){
        return findAll().size();
    }

    public Optional<T> findById(Long id) {
        return null;
    }

    private void leerObjetosDesdeJson(){

    }
}




