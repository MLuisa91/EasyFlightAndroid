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
public class Aeropuerto implements Serializable {

    private Integer id;

    private String nombre;

    public String toString() {
        return nombre;

    }

}
