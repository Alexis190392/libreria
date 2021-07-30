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
    
    //buscar todos
    public List<Autor> listAll(){
        return ar.findAll();
    }
    
    //buscar uno por nombre - buscador general
    
    public Autor searchName(String nombre) throws Exception{
        try{
            for (Autor a : listAll()) {         //busca en el listado de personas uno a uno
                if(a.getNombre().toLowerCase().contains(nombre.toLowerCase())){ //compara cada caso con el nombre del objeto en esa iteracion
                    return a;
                } else if(searchId(nombre)!= null){  //sino, busca en id, si no lo encuentra, va al siguiente ciclo del each
                    return searchId(nombre);
                }
            }
        } catch (Exception e){
            throw new Exception("no se encuentra autor");
        } 
        return null; //si no encuentra coincidencias, no retorna nada
    }
    
    //buscar uno por id
    public Autor searchId(String id) throws Exception{
        try{
            return ar.getById(id);
        }catch (Exception e){
            throw new Exception("no se encuentra autor");
        } 
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
