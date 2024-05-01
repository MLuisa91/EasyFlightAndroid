package com.example.flightextrem.components;

import java.io.Serializable;
import java.time.LocalDate;

public class ListReservas implements Serializable{
    private String idReserva;
    private LocalDate fechaReserva;
    private Float total;

    public ListReservas(String idReserva, LocalDate fechaReserva, Float total) {
        this.idReserva = idReserva;
        this.fechaReserva = fechaReserva;
        this.total = total;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
