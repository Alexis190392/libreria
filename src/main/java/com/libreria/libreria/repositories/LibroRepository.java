package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>{
    
    
    //creo la query de busqueda de libro
    @Query("select l from Libro l where "
            + "l.titulo LIKE :query or "
            + "l.anio LIKE :query or "
            + "l.autor LIKE :query or "
            + "l.editorial LIKE :query or "
            + "l.isbn = :queryIsbn")
    List<Libro> findByQuery(@Param("query") String query, @Param("queryIsbn") Long queryIsbn);

}
