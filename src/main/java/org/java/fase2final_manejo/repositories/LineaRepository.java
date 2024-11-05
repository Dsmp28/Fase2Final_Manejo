package org.java.fase2final_manejo.repositories;

import org.java.fase2final_manejo.models.Linea;

import java.util.List;
import java.util.stream.Collectors;

public class LineaRepository extends GenericRepository<Linea> {

    public LineaRepository(String dataFilePath, String indexFilePath) {
        super(dataFilePath, indexFilePath, Linea.class);
    }

    public List<Linea> obtenerLineasPorMarca(Long id) {
        return findAll().stream() // Llama a findAll() para obtener todas las líneas
                .filter(linea -> linea.getMarca().getId().equals(id)) // Filtra por id de Marca
                .collect(Collectors.toList()); // Recoge los resultados en una lista
    }

    public List<Linea> findByNombreLineaContainingIgnoreCase(String nombreLinea) {
        return searchByName(nombreLinea);
    }

}




