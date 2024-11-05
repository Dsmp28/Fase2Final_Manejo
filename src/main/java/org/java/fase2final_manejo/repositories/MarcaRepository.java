package org.java.fase2final_manejo.repositories;

import org.java.fase2final_manejo.models.Marca;

import java.util.List;

public class MarcaRepository extends GenericRepository<Marca>{

    public MarcaRepository(String dataFilePath, String indexFilePath) {
        super(dataFilePath, indexFilePath, Marca.class);
    }

    public List<Marca> findByNombreContainingIgnoreCase(String nombre) {
        return searchByName(nombre);
    }
}
