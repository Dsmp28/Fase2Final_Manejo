package org.java.fase2final_manejo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehiculo {

    public static long contadorId = 0;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marca")
    private Marca marca;

    @JsonProperty("modelo")
    private String modelo;

    @JsonProperty("color")
    private String color;

    @JsonProperty("placa")
    private String placa;

    @JsonProperty("chasis")
    private String chasis;

    @JsonProperty("motor")
    private String motor;

    @JsonProperty("vin")
    private String vin;

    @JsonProperty("asientos")
    private Integer asientos;

    @JsonProperty("tipo")
    private Tipo tipo;

    @JsonProperty("linea")
    private Linea linea;


    public Vehiculo (Marca marca, String modelo, String color, String placa, String chasis, String motor, String vin, Integer asientos, Tipo tipo, Linea linea){
        this.id = contadorId++;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.chasis = chasis;
        this.motor = motor;
        this.vin = vin;
        this.asientos = asientos;
        this.tipo = tipo;
        this.linea = linea;
    }

    public Vehiculo(){
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @JsonIgnore
    public String getNombre() {
        return placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
