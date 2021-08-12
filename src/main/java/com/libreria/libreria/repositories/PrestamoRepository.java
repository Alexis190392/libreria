/*
 El repositorio que persiste a esta
entidad (PrestamoRepositorio) debe contener los métodos necesarios para registrar un
préstamo en la base de datos, realizar consultas y realizar devoluciones, etc
*/

package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo,String>{
    
    @Query("select p from Prestamo p where p.id like :query or p.libro.titulo like :query or p.cliente.documento = :queryDoc")
    List<Prestamo> findAll(@Param("query") String query, @Param("queryDoc") Long queryDoc);
    
    @Query("select p from Prestamo p where p.cliente.documento = : dni")
    List<Prestamo> findByDni(@Param("dni") Long dni);
    
    @Query("select p from Prestamo p where p.libro.titulo like :titulo")
    List<Prestamo> findByLibro(@Param("titulo") String titulo);
    
}
