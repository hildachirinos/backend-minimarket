package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMovimientoInventario extends DtoGeneric {
    private String idMovimiento;

    private String idProducto;

    private String codigoProducto;

    private String nombreProducto;

    private String tipoMovimiento;

    private int cantidad;

    private int stockAnterior;

    private int stockResultante;

    private String motivo;

    private String observacion;

    private String documentoReferencia;
}
