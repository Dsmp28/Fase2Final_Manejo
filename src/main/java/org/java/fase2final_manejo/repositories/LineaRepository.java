package org.java.fase2final_manejo.repositories;

import org.java.fase2final_manejo.models.Linea;

import java.util.List;

public class LineaRepository extends GenericRepository<Linea> {

    public LineaRepository(String dataFilePath, String indexFilePath) {
        super(dataFilePath, indexFilePath, Linea.class);
    }

    public List<Linea> obtenerLineasPorMarca(Long id) {
        return null;
    }

    public List<Linea> findByNombreLineaContainingIgnoreCase(String nombreLinea) {
        return null;
    }

}




