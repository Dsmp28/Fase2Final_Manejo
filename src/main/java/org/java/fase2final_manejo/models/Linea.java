package org.java.fase2final_manejo.models;

public class Linea {

    public Linea (Marca marca, String nombreLinea, Integer ano){
        this.marca = marca;
        this.nombreLinea = nombreLinea;
        this.ano = ano;
    }

    private Long id;

    private Marca marca;

    private String nombreLinea;

    private Integer ano;

    @Override
    public String toString() {
        return nombreLinea;
    }

    public String getNombreMarca() {
        return marca.getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getNombreLinea() {
        return nombreLinea;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
