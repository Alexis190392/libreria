package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.excepciones.WebException;
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
    
    
    //guardar
    @Transactional
    public Libro save(Libro libro) throws WebException{
        //validaciones
        if(libro.getTitulo().isEmpty() || libro.getTitulo() == null){
            throw new WebException("El Titulo no puede estar vacio.");
        }
        if(libro.getAnio() == null){
            throw new WebException("El año no puede estar vacio.");
        } 
        if(libro.getEjemplares() == null){
            throw new WebException("La cantidad de ejemplares no puede estar vacio.");
        }
        if(libro.getPrestados() == null){
            throw new WebException("La cantidad de prestados no puede estar vacio.");
        } else if(libro.getPrestados()> libro.getEjemplares()){
            throw new WebException("La cantidad de prestados no puede ser mayor a la cantidad de ejemplares.");
        }
        if(libro.getAutor() == null){
            throw new WebException("El Autor no puede estar vacio.");
        }
        if(libro.getEditorial() == null){
            throw new WebException("La editorial no puede estar vacia.");
        }
        return lr.save(libro);
    }
    
    @Transactional
    public Libro save(Long isbn, String titulo,Integer anio,Integer ejemplares,Integer prestados,Autor autor, Editorial editorial){
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
        
        Long isbn;
        try{
            isbn = Long.parseLong(query);   
        } catch (Exception e){
            isbn= null;
        }
        return lr.findByQuery("%"+query+"%", isbn);
    }
    
    public List<Libro> listAllByAutor(String nombre){
        return lr.findAllByAutor(nombre);
    }
    
    public List<Libro> listAllByEditorial(String nombre){
        return lr.findAllByEditorial(nombre);
    }
    
    public Libro findById(Long isbn){
        Libro libro= null;
        Optional<Libro> op = lr.findById(isbn);
        if(op.isPresent()){
            libro = op.get();
        }
        return libro;
    }
    
    @Transactional
    public void delete (Libro libro){
        lr.delete(libro);
    }
    
    //Para lños casos en que se elimine una editorial o autor
    //y no nos elimine desde todos los libros asociados sin afectar lo demas
    @Transactional
    public void deleteFieldAutor(Autor autor){
        List<Libro> libros = listAllByAutor(autor.getNombre());
        for (Libro libro : libros) {
            libro.setAutor(null);
        }
        lr.saveAll(libros);
    }
    
    @Transactional
    public void deleteFieldEditorial(Editorial editorial){
        List<Libro> libros = listAllByAutor(editorial.getNombre());
        for (Libro libro : libros) {
            libro.setEditorial(null);
        }
        lr.saveAll(libros);
    }
    
    @Transactional 
    public void deleteByIsbn(Long isbn){
        Optional<Libro> op = lr.findById(isbn);
        if(op.isPresent()){
            lr.delete(op.get()); //nos trae como objeto para eliminarlo
        }
    }
}