package com.example.flightextrem.service.pojo;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reserva implements Serializable {


    private Integer id;
    private String code;

    private Usuario usuario;

    private Vuelo vueloIda;
    private Vuelo vueloVuelta;

    private Oferta oferta;

    private Integer numPasajeros;

    private Double total;

    private Set<ReservaExtra> reservaExtras;
    @SerializedName("fechaReserva")
    private LocalDate fechaReserva;
    Set<ReservaViajero> reservaViajeros;

    public Reserva(Vuelo vueloIda){
        this.vueloIda = vueloIda;
    }
}
