package org.java.fase2final_manejo.models;

import java.time.LocalDate;


public class Marca {

    private Long id;

    private String nombre;

    private LocalDate fechaCreacion;

    private String fundador;

    @Override
    public String toString() {
        return nombre;
    }

    public Marca (String nombre, LocalDate fechaCreacion, String fundador){
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fundador = fundador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFundador() {
        return fundador;
    }

    public void setFundador(String fundador) {
        this.fundador = fundador;
    }
}
