package com.epiis.app.entity;

import java.math.BigDecimal;
import java.util.List;

import com.epiis.app.generic.EntityGeneric;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tproducto")
@Getter
@Setter
public class Producto extends EntityGeneric {
    @Id
    @Column(name = "idProducto")
    private String idProducto;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precioCompra")
    private BigDecimal precioCompra;

    @Column(name = "precioVenta")
    private BigDecimal precioVenta;

    @Column(name = "stockActual")
    private int stockActual;

    @Column(name = "stockMinimo")
    private int stockMinimo;

    @Column(name = "unidadMedida")
    private String unidadMedida;

    @Column(name = "activo")
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "producto")
    private List<MovimientoInventario> listMovimiento;
}
