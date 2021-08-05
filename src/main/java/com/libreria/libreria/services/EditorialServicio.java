package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.excepciones.WebException;
import com.libreria.libreria.repositories.EditorialRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepository er;
    
    @Transactional
    public Editorial save(Editorial a) throws WebException{
        if(a.getNombre().isEmpty() || a.getNombre() == null){
            throw new WebException("No se ha encontado Editorial que coincida con la busqueda");
        }
        return er.save(a);
    }
    
    @Transactional
    public Editorial save(String nombre){
        Editorial a = new Editorial();
        a.setNombre(nombre);
        return er.save(a);
    }
    
                    /*    BUSQUEDA    */
    
    //buscar todos
    public List<Editorial> listAll(){
        return er.findAll();
    }
    
    //buscador general
    public List<Editorial> listByQuery(String query) {
        return er.searchId("%" + query + "%");
    }
    
    public Optional<Editorial> searchId(String id){
        return er.findById(id);
    }
    
    /*   ELIMINAR     */
    
    public void delete(Editorial a){
        er.delete(a);
    }
    
    public void deleteById(String id){
        Optional<Editorial> op = er.findById(id);
        if(op.isPresent()){
            er.delete(op.get());
        }
    }
}