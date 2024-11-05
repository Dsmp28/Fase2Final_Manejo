package org.java.fase2final_manejo.services;

import org.java.fase2final_manejo.models.Linea;
import org.java.fase2final_manejo.repositories.LineaRepository;

import java.io.IOException;
import java.util.List;

public class LineaService {

    private final LineaRepository lineaRepository;

    public LineaService() {
        this.lineaRepository = new LineaRepository("src/main/resources/org/java/fase2final_manejo/Data/dataLinea.json",
                "src/main/resources/org/java/fase2final_manejo/Data/indexLinea.txt");
    }

    public List<Linea> obtenerTodoslasLineas() {
        List<Linea> lineas = lineaRepository.findAll();
        return lineas;
    }

    public void guardarLinea(Linea linea){
        lineaRepository.save(linea);
    }

    public void eliminarLinea(Long id){
        lineaRepository.deleteById(id);
    }

    public List<Linea> obtenerLineaPorMarca(Long id){
        return lineaRepository.obtenerLineasPorMarca(id);
    }

    public List<Linea> buscarLineaPorNombre(String nombreLinea) {
        List<Linea> listaBusqueda = lineaRepository.findByNombreLineaContainingIgnoreCase(nombreLinea);

        return listaBusqueda;
    }

    public long contarLineas(){
        return lineaRepository.count();
    }
}



