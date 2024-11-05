package org.java.fase2final_manejo.services;

import org.java.fase2final_manejo.models.Tipo;
import org.java.fase2final_manejo.repositories.TipoRepository;

import java.util.List;
import java.util.Optional;

public class TipoService {
    private final TipoRepository tipoRepository;

    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }
    public List<Tipo> obtenerTodoslosTipos(){
        return tipoRepository.findAll();
    }
    public void guardarTipo(Tipo tipo){
        tipoRepository.save(tipo);
    }

    public void eliminarTipo(Long id){
        tipoRepository.deleteById(id);
    }

    public List<Tipo> buscarTipoPorNombre(String nombreTipo){
        return tipoRepository.findByNombreTipoContainingIgnoreCase(nombreTipo);
    }

    public long contarTipos(){
        return tipoRepository.count();
    }
}
