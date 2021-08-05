package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{
    
    //Query de busqueda del cliente
    @Query("select c from Cliente c where "
            + "c.documento = :queryDni or "
            + "c.nombre LIKE :query or "
            + "c.apellido LIKE :query or "
            + "c.domicilio LIKE :query or "
            + "c.telefono LIKE :query")
    List<Cliente> findByQuery(@Param("query") String query, @Param("queryDni") Long queryDni);
    
}
