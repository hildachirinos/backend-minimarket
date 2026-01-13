package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epiis.app.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, String> {
    @Query("""
                select c
                from Categoria c
                where c.activo = true
                order by c.nombre
            """)
    List<Categoria> getAllActivas();

    @Query("""
                select c
                from Categoria c
                order by c.nombre
            """)
    List<Categoria> getAll();

    @Query("""
                select c
                from Categoria c
                left join fetch c.listProducto p
                where c.idCategoria = :idCategoria
            """)
    Categoria getByIdWithProductos(String idCategoria);
}
