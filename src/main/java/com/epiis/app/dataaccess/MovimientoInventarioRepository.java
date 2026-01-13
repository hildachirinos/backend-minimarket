package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epiis.app.entity.MovimientoInventario;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, String> {
    @Query("""
                select m
                from MovimientoInventario m
                left join fetch m.producto p
                order by m.createdAt desc
            """)
    List<MovimientoInventario> getAll();

    @Query("""
                select m
                from MovimientoInventario m
                left join fetch m.producto p
                where p.idProducto = :idProducto
                order by m.createdAt desc
            """)
    List<MovimientoInventario> getByProducto(String idProducto);

    @Query("""
                select m
                from MovimientoInventario m
                left join fetch m.producto p
                where m.tipoMovimiento = :tipoMovimiento
                order by m.createdAt desc
            """)
    List<MovimientoInventario> getByTipo(MovimientoInventario.TipoMovimiento tipoMovimiento);
}
