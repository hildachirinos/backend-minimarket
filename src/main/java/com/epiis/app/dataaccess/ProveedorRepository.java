package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epiis.app.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, String> {
    @Query("""
                select p
                from Proveedor p
                where p.activo = true
                order by p.razonSocial
            """)
    List<Proveedor> getAllActivos();

    @Query("""
                select p
                from Proveedor p
                order by p.razonSocial
            """)
    List<Proveedor> getAll();

    @Query("""
                select p
                from Proveedor p
                where p.ruc = :ruc
            """)
    Proveedor findByRuc(String ruc);
}
