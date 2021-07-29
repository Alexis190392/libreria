package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,String>{
    
}
