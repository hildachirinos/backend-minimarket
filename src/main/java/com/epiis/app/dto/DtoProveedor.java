package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProveedor extends DtoGeneric {
    private String idProveedor;

    private String ruc;

    private String razonSocial;

    private String direccion;

    private String telefono;

    private String email;

    private String contacto;

    private boolean activo;

    private int cantidadProductos;
}
