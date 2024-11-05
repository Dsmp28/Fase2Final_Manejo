package org.java.fase2final_manejo.repositories;

import org.java.fase2final_manejo.models.Vehiculo;

import java.util.List;

public class VehiculoRepository extends GenericRepository<Vehiculo> {
    public VehiculoRepository(String dataFilePath, String indexFilePath) {
        super(dataFilePath, indexFilePath, Vehiculo.class);
    }

    public List<Vehiculo> findByPlacaContainingIgnoreCase(String placa) {
        return searchByName(placa);
    }
}
