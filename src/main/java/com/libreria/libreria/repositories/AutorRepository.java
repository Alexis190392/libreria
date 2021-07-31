package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor,String>{
    
    @Query("SELECT a FROM Autor a where a.nombre LIKE :query")
    List<Autor> searchId(@Param("query") String query);
}
