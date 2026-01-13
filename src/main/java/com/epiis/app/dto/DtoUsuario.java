package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUsuario extends DtoGeneric {
    private String idUsuario;

    private String userName;

    private String password;

    private String nombre;

    private String email;

    private String rol;

    private boolean activo;
}
