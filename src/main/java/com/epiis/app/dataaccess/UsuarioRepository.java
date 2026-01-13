package com.epiis.app.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epiis.app.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query("""
                select u
                from Usuario u
                where u.userName = :userName and u.activo = true
            """)
    Usuario findByUserName(String userName);

    @Query("""
                select u
                from Usuario u
                where u.userName = :userName and u.password = :password and u.activo = true
            """)
    Usuario findByUserNameAndPassword(String userName, String password);
}
