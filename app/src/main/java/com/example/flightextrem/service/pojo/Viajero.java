package com.example.flightextrem.service.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viajero implements Serializable {


    private Integer id;

    private String dni;

    private String nombre;

    private String apellidos;

    //@SerializedName("fechaNacimiento")
    private LocalDate fechaNacimiento;

}
