package org.java.fase2final_manejo.models;

public class Tipo {

    public Tipo(String nombreTipo, Integer ano) {
        this.nombreTipo = nombreTipo;
        this.ano = ano;
    }

    private Long id;

    private String nombreTipo;

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
