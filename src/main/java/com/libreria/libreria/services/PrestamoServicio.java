package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.entidades.Prestamo;
import com.libreria.libreria.excepciones.WebException;
import com.libreria.libreria.repositories.PrestamoRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {
    
    @Autowired
    private PrestamoRepository pr;
    
    //para hacer un prestamo
    @Transactional
    public Prestamo registrar(Prestamo p) throws WebException{
        if(p.getLibro() == null){
            throw new WebException("El campo libro no puede estar vacio");
        }
        if(p.getCliente() == null){
            throw new WebException("El campo cliente no puede estar vacio");
        }
        if(p.getDevolucion().before(new Date())){
            throw new WebException("La fecha no puede ser anterior al dia de hoy");
        }
        System.err.println(p.toString());
        return pr.save(p);
    }
    
    @Transactional
    public Prestamo registrar(Date devolucion, Libro libro, Cliente cliente){
        Prestamo p = new Prestamo();
        p.setFecha(new Date());
        p.setDevolucion(devolucion);
        p.setLibro(libro);
        p.setCliente(cliente);
        //operaciones sobre el libro
        Libro l = libro;
        l.setEjemplares(p.getLibro().getEjemplares() - 1);
        l.setPrestados(p.getLibro().getPrestados() + 1);
        
        p.setLibro(l);
        /*
        cambio lineas anteriores para setear modificaciones en el libro respecto a los ejemplares diponibles y prestados
        */
        return pr.save(p);
    }
  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //Listar prestamos
    public List<Prestamo> listAll(){ //listar todo lo de la base de datos
        return pr.findAll(); 
    }
    
    public List<Prestamo> listAllByDni(String documento){ //listar todo lo de la base de datos con dni traido del front
        Long dni; //creo variable, para convertir de string a long
        try{ //por si se envia nulo o no se puede convertir a long(caso de ingreso de letras)
            dni= Long.parseLong(documento); //conversion
        } catch (Exception e){
            dni=null;
        }
        return pr.findByDni(dni);
    }
    
    public List<Prestamo> listAllByLibro(String libro){
        return pr.findByLibro("%"+libro+"%");
    }
    
    public Optional<Prestamo> findById(String id){
        return pr.findById(id);
    }
            

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   
    
    
    
    //devolver libro y asignar al stock
    public void devolucion(Prestamo prestamo) throws WebException{
        prestamo.setDevolucion(new Date());
        prestamo.getLibro().setEjemplares(prestamo.getLibro().getEjemplares() + 1);
        prestamo.getLibro().setPrestados(prestamo.getLibro().getPrestados() - 1);
        
        registrar(prestamo);
    }
    
    public void multa(){
        
    }
    
    


}
