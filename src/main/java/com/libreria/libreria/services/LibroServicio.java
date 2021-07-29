package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.repositories.LibroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepository lr;
    @Autowired
    private AutorServicio as;
    @Autowired
    private EditorialServicio es;
    
    
    
    public List<Libro> listAll(){
        return lr.findAll(); 
    }
    
    public Libro save(Libro libro){
        return lr.save(libro);
    }
    
    public Libro save(Long isbn, String titulo,Integer anio,Integer ejemplares,Integer prestados,String autor,String editorial){
        Libro l = new Libro();
        l.setAnio(anio);
        l.setAutor(autor);
        l.setEditorial(editorial);
        l.setEjemplares(ejemplares);
        l.setIsbn(isbn);
        l.setPrestados(prestados);
        l.setTitulo(titulo);
        return lr.save(l);
    }
    
    public String delete(Libro libro){
        
        if(lr.findById(libro.getIsbn().toString()).isPresent()){
            lr.delete(libro);
            return "Libro Eliminado";
        } else{
            return "Libro no existe";
        }
    }
}