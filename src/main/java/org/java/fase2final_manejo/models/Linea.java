package org.java.fase2final_manejo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Linea {

    public static long contadorId = 0;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marca")
    private Marca marca;

    @JsonProperty("nombreLinea")
    private String nombreLinea;

    @JsonProperty("ano")
    private Integer ano;

    public Linea(Marca marca, String nombreLinea, Integer ano) {
        this.id = contadorId++;
        this.marca = marca;
        this.nombreLinea = nombreLinea;
        this.ano = ano;
    }

    public Linea(){

    }

    public Long getId() {
        return id;
    }


    public Marca getMarca() {
        return marca;
    }


    public String getNombreLinea() {
        return nombreLinea;
    }

    public Integer getAno() {
        return ano;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}

