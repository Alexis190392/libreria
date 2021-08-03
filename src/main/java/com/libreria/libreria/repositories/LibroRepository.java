package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,String>{
    
    
    //creo la query de busqueda de libro
    @Query("select l from Libro l where "
            + "l.titulo LIKE :query or "
            + "l.anio LIKE :query or "
            + "l.autor LIKE :query or "
            + "l.editorial LIKE :query")
    List<Libro> findByQuery(@Param("query") String query);
    
    
//    @Query("select l from Libro l where l.isbn = :isbn")
//    List<Libro> findByIsbn(@Param("isbn") Long isbn);
//    
//    
//    @Query("select l from Libro l where l.anio = :anio")
//    List<Libro> findByQuery(@Param("anio") Integer anio);

    
    ////////////////////modifico a optional para buscar y traer solo ese resultado/////////////////////////////////
    @Query("select l from Libro l where l.isbn = :isbn")
    Optional<Libro> findById(@Param("isbn") Long isbn);
    
    
}
