package com.example.flightextrem.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaViajero {

    private ReservaViajeroPK id;

    private Reserva reserva;

    private Viajero viajero;
}
