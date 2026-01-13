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
@Table(name = "tcategoria")
@Getter
@Setter
public class Categoria extends EntityGeneric {
    @Id
    @Column(name = "idCategoria")
    private String idCategoria;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> listProducto;
}
