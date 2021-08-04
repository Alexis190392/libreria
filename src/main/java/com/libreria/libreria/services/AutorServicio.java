package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.excepciones.WebException;
import com.libreria.libreria.repositories.AutorRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepository ar;
    
    @Transactional
    public Autor save(Autor a) throws WebException{
        if(a.getNombre().isEmpty() || a.getNombre() == null){
            throw new WebException("No se ha encontado Autor que coincida con la busqueda");
        }
        return ar.save(a);
    }
    
    @Transactional
    public Autor save(String nombre){
        Autor a = new Autor();
        a.setNombre(nombre);
        return ar.save(a);
    }
    
                    /*    BUSQUEDA    */
    
    //buscar todos
    public List<Autor> listAll(){
        return ar.findAll();
    }
    
    //buscador general
    public List<Autor> listByQuery(String query) {
        return ar.searchId("%" + query + "%");
    }
    
    public Optional<Autor> searchId(String id){
        return ar.findById(id);
    }
    
    /*   ELIMINAR     */
    
    public void delete(Autor a){
        ar.delete(a);
    }
    
    public void deleteById(String id){
        Optional<Autor> op = ar.findById(id);
        if(op.isPresent()){
            ar.delete(op.get());
        }
    }
}
