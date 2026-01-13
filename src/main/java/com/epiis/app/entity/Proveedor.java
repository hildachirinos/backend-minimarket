package com.epiis.app.entity;

import java.util.List;

import com.epiis.app.generic.EntityGeneric;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tproveedor")
@Getter
@Setter
public class Proveedor extends EntityGeneric {
    @Id
    @Column(name = "idProveedor")
    private String idProveedor;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "contacto")
    private String contacto;

    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "proveedor")
    private List<Producto> listProducto;
}
