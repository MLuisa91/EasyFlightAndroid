package com.example.flightextrem.components;

import java.time.LocalDate;

public class ListVuelos {
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private Double precio;

    public ListVuelos(String origen, String destino, LocalDate fechaSalida, Double precio) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
