package com.example.flightextrem.service.pojo;


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


    private String id;

    private Usuario usuario;

    private Vuelo vuelo;

    private Oferta oferta;

    private Integer numPasajeros;

    private Double total;

    private Set<ReservaExtra> reservaExtras;
    private LocalDate fechaReserva;
    Set<ReservaViajero> reservaViajeros;

}
