package com.libreria.libreria.repositories;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial,String>{
    @Query("SELECT e FROM Editorial e where e.nombre LIKE :query")
    List<Editorial> searchId(@Param("query") String query);
}
