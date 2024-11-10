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
    private String dataFilePath;
    private String indexFilePath;
    private ObjectMapper objectMapper = new ObjectMapper();

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
}

