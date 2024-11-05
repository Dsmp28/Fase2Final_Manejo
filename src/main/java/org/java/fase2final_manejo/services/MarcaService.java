package org.java.fase2final_manejo.services;

import org.java.fase2final_manejo.models.Marca;
import org.java.fase2final_manejo.repositories.MarcaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }

    public void guardarMarca(Marca marca) {
        if (marca.getFechaCreacion() == null) {
            marca.setFechaCreacion(LocalDate.now());  // Asegurarse de que la fecha de creación esté presente
        }
        marcaRepository.save(marca);
    }

    public void eliminarMarca(Long id) {
        marcaRepository.deleteById(id);
    }

    public List<Marca> buscarMarcaPorNombre(String nombre) {
        return marcaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public long contarMarcas() {
        return marcaRepository.count();
    }

    public void actualizarMarca(Long id, Marca marca) {
        marcaRepository.save(marca);
    }
}
