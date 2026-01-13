package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epiis.app.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    @Query("""
                select p
                from Producto p
                left join fetch p.categoria c
                left join fetch p.proveedor prov
                where p.activo = true
                order by p.nombre
            """)
    List<Producto> getAllActivos();

    @Query("""
                select p
                from Producto p
                left join fetch p.categoria c
                left join fetch p.proveedor prov
                order by p.nombre
            """)
    List<Producto> getAll();

    @Query("""
                select p
                from Producto p
                left join fetch p.categoria c
                left join fetch p.proveedor prov
                where p.idProducto = :idProducto
            """)
    Producto getByIdWithRelations(String idProducto);

    @Query("""
                select p
                from Producto p
                where p.codigo = :codigo
            """)
    Producto findByCodigo(String codigo);

    @Query("""
                select p
                from Producto p
                left join fetch p.categoria c
                left join fetch p.proveedor prov
                where p.activo = true and p.stockActual <= p.stockMinimo
                order by p.stockActual
            """)
    List<Producto> getProductosConStockBajo();

    @Query("""
                select p
                from Producto p
                left join fetch p.categoria c
                where p.categoria.idCategoria = :idCategoria and p.activo = true
                order by p.nombre
            """)
    List<Producto> getByCategoria(String idCategoria);
}
