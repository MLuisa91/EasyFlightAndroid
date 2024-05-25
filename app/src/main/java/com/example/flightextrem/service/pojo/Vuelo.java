package com.example.flightextrem.service.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vuelo implements Serializable {

    private String id;

    private Aeropuerto origen;

    private Aeropuerto destino;

    @SerializedName("fechaSalida")
    private LocalDate fechaSalida;

    private LocalTime horaSalida;

    private LocalTime horaLlegada;

    private Avion avion;
    private Double precio;

    public Vuelo(String id) {
        this.id = id;
    }


}
