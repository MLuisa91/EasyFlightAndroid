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
public class UsuarioRol implements Serializable {

    private UsuarioRolPK id;

    private Usuario usuario;

    private Rol rol;

    public UsuarioRol(Usuario usuario, Rol rol) {
        if (usuario != null && rol != null)
            this.id = new UsuarioRolPK(usuario.getId(), rol.getId());

        this.usuario = usuario;
        this.rol = rol;
    }


}
