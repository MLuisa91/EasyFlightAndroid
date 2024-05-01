package com.example.flightextrem.components;

import java.io.Serializable;
import java.time.LocalDate;

public class ListPersonas implements Serializable{
    private String nombre;
    private String apellidos;
    private LocalDate nacimiento;
    private String dni;

    public ListPersonas(String nombre, String apellidos, LocalDate nacimiento, String dni) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacimiento = nacimiento;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
