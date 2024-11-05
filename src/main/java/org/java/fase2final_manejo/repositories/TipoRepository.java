package org.java.fase2final_manejo.repositories;

import org.java.fase2final_manejo.models.Tipo;

import java.util.List;

public class TipoRepository extends GenericRepository<Tipo> {
    public TipoRepository(String dataFilePath, String indexFilePath) {
        super(dataFilePath, indexFilePath, Tipo.class);
    }

    public List<Tipo> findByNombreTipoContainingIgnoreCase(String nombreTipo) {
        return null;
    }
}
