package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.repositories.LibroRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepository lr;
//    @Autowired
//    private AutorServicio as;
//    @Autowired
//    private EditorialServicio es;
    
    
    
    //guardar
    @Transactional
    public Libro save(Libro libro){
        return lr.save(libro);
    }
    
    @Transactional
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
    
    
    public List<Libro> listAll(){
        return lr.findAll(); 
    }
    
    
    //buscador general de libros en la db
    public List<Libro> findByQuery(String query){
        return lr.findByQuery("%"+query+"%");
    }
    
    public Optional<Libro> findByIsbn(Long isbn){
        return lr.findById(isbn);
    }
    
//    public List<Libro> findByQuery(Integer anio){
//        return lr.findByQuery(anio);
//    }
    
    
    
    

    
    //  Anterior
//    public String delete(Libro libro){
//        
//        if(lr.findById(libro.getIsbn().toString()).isPresent()){
//            lr.delete(libro);
//            return "Libro Eliminado";
//        } else{
//            return "Libro no existe";
//        }
//    }
    
    
    //ahora el delete
    
    public void delete (Libro libro){
        lr.delete(libro);
    }
    
    public void deleteByIsbn(Long isbn){
        Optional<Libro> op = lr.findById(isbn);
        if(op.isPresent()){
            lr.delete(op.get()); //nos trae como objeto para eliminarlo
        }
    }
}
