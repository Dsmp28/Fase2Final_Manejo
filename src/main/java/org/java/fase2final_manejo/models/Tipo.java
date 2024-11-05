package org.java.fase2final_manejo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tipo {

    public Tipo(String nombreTipo, Integer ano) {
        this.id = contadorId++;
        this.nombreTipo = nombreTipo;
        this.ano = ano;
    }

    public Tipo(){

    }
    public static long contadorId = 0;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombreTipo")
    private String nombreTipo;

    @JsonProperty("ano")
    private Integer ano;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return nombreTipo;
    }
}
