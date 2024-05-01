package com.example.flightextrem.service.pojo;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Avion implements Serializable {

    private String id;

    private String modelo;

    private Integer pasajeros;
}
