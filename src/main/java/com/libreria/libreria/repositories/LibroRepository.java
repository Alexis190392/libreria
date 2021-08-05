package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Libro;
import java.util.List;
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
            + "l.autor.nombre LIKE :query or "
            + "l.editorial.nombre LIKE :query or "
            + "l.isbn = :queryIsbn")
    List<Libro> findByQuery(@Param("query") String query, @Param("queryIsbn") Long queryIsbn);
    
    @Query("select l from Libro l where l.autor.nombre like :query")
    List<Libro> findAllByAutor(@Param("q") String query);
    
    @Query("select l from Libro l where l.editorial.nombre like :query")
    List<Libro> findAllByEditorial(@Param("q") String query);
    
}
