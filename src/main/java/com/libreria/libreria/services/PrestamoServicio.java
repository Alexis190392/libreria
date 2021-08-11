package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.entidades.Prestamo;
import com.libreria.libreria.repositories.PrestamoRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {
    
    @Autowired
    private PrestamoRepository pr;
    
    //para hacer un prestamo
    @Transactional
    public Prestamo registrar(Prestamo prestamo){
        return pr.save(prestamo);
    }
    
    @Transactional
    public Prestamo registrar(Date devolucion, Libro libro, Cliente cliente ){
        Prestamo p = new Prestamo();
        p.setFecha(new Date());
        p.setDevolucion(devolucion);
        p.setLibro(libro);
        //operaciones sobre el libro
        p.getLibro().setEjemplares(p.getLibro().getEjemplares() - 1);
        p.getLibro().setPrestados(p.getLibro().getPrestados() + 1);
        
        p.setCliente(cliente);
        return pr.save(p);
    }
    
    //consultar prestamos
    public List<Prestamo> consultar(String query){
        Long doc;
        try{
            doc = Long.parseLong(query);   
        } catch (Exception e){
            doc= null;
        }
        return pr.findAll("%"+query+"%", doc);
    }
    
    //devolver libro y asignar al stock
    public void devolucion(Prestamo prestamo){
        prestamo.setDevolucion(new Date());
        prestamo.getLibro().setEjemplares(prestamo.getLibro().getEjemplares() + 1);
        prestamo.getLibro().setPrestados(prestamo.getLibro().getPrestados() - 1);
        
        registrar(prestamo);
    }
    
    public void multa(){
        
    }
    
    


}
