package com.example.flightextrem.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRol {

    private UsuarioRolPK id;

    private Usuario usuario;

    private Rol rol;

}
