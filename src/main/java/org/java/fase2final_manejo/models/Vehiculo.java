package org.java.fase2final_manejo.models;

public class Vehiculo {

    private Long id;

    private Marca marca;

    private String modelo;

    private String color;

    private String placa;

    private String chasis;

    private String motor;

    private String vin;

    private Integer asientos;

    private Tipo tipo;

    private Linea linea;


    public Vehiculo (Marca marca, String modelo, String color, String placa, String chasis, String motor, String vin, Integer asientos, Tipo tipo, Linea linea){
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
