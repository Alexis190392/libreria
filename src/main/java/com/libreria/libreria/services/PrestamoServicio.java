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
    
    @Transactional
    public Prestamo save(Prestamo p) throws WebException{
        if(p.getCliente() == null){
            throw new WebException("El cliente no puede estar vacio.");
        }
        if(p.getLibro() == null){
            throw new WebException("El libro no puede estar vacio.");
        }
        if(p.getDevolucion().before(new Date())){
            throw new WebException("la fecha no puede ser igual a la actual");
        }
        p.setFecha(new Date());
        return pr.save(p);
    }
    
    @Transactional
    public Prestamo save(Cliente cliente, Libro libro, Date fecha){
        Prestamo p = new Prestamo();
        p.setCliente(cliente);
        p.setLibro(libro);
        p.setFecha(new Date());
        p.setDevolucion(fecha);

        return pr.save(p);
    }
    
    public List<Prestamo> listAll(){
        return pr.findAll();
    }
    
    @Transactional
    public void delete(Prestamo p){
        pr.delete(p);
    }
   
}
