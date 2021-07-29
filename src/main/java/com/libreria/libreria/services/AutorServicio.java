package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.repositories.AutorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepository ar;
    
    public List<Autor> listAll(){
        return ar.findAll();
    }
    
    public Autor save(Autor autor){
        return ar.save(autor);
    }
    
    public Autor save(String nombre){
        Autor a = new Autor();
        a.setNombre(nombre);
        return ar.save(a);
    }
    
    public void delete(Autor a){
        if(ar.findById(a.getId()).isPresent()){
            ar.delete(a);
        }
    }
}
