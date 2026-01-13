package com.epiis.app.dto;

import java.math.BigDecimal;

import com.epiis.app.generic.DtoGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProducto extends DtoGeneric {
    private String idProducto;

    private String codigo;

    private String nombre;

    private String descripcion;

    private String idCategoria;

    private String nombreCategoria;

    private String idProveedor;

    private String nombreProveedor;

    private BigDecimal precioCompra;

    private BigDecimal precioVenta;

    private int stockActual;

    private int stockMinimo;

    private String unidadMedida;

    private boolean activo;

    private boolean stockBajo;
}
