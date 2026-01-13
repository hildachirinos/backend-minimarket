package com.epiis.app.entity;

import com.epiis.app.generic.EntityGeneric;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tmovimiento_inventario")
@Getter
@Setter
public class MovimientoInventario extends EntityGeneric {

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }

    @Id
    @Column(name = "idMovimiento")
    private String idMovimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoMovimiento")
    private TipoMovimiento tipoMovimiento;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "stockAnterior")
    private int stockAnterior;

    @Column(name = "stockResultante")
    private int stockResultante;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "documentoReferencia")
    private String documentoReferencia;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
}
