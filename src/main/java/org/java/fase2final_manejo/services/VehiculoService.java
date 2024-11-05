package org.java.fase2final_manejo.services;

import org.java.fase2final_manejo.models.Vehiculo;
import org.java.fase2final_manejo.repositories.VehiculoRepository;

import java.util.List;
import java.util.Optional;

public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }
    public List<Vehiculo> obtenerTodoslosVehiculos(){
        return vehiculoRepository.findAll();
    }
    public void guardarVehiculo(Vehiculo vehiculo){
        vehiculoRepository.save(vehiculo);
    }
    public void eliminarVehiculo(Long id){
        vehiculoRepository.deleteById(id);
    }

    public long contarVehiculos(){
        return vehiculoRepository.count();
    }

    public List<Vehiculo> buscarVehiculoPorPlaca(String placa){
        return vehiculoRepository.findByPlacaContainingIgnoreCase(placa);
    }
}
