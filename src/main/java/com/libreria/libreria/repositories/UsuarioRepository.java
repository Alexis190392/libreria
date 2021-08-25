package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
