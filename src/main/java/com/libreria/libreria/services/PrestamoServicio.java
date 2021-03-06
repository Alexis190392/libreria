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

/*
    -> Guardar  -> CREAR PRESTAMO - EDITAR PRESTAMO
        -> setear el libro con los ejemplares y prestados para saber la canTidad disponible de libros (metodo comprobar)
    -> Listar   -> ADMINISTRAR PRESTAMO
        -> Eliminar -> boton eliminar
        -> Modificar-> con el metodo de guardar - boton modificar
        -> Renovar  -> verificar fecha anterior de devolucion o aplicar multa - boton renovar
        -> Devolver -> verificar fecha actual con la seteada anteriormente, caso de ser posterior, utilizar metodo multa
    -> Multa    -> (metodo) calcular diferencia de fecha y multiplicar por valor de multa
    
*/



@Service
public class PrestamoServicio {
    
    @Autowired
    private PrestamoRepository pr;
    
    private Double precioMulta = 50d;
    
    /*              GUARDAR Y MODIFICAR              */
    
    
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
        p.setFecha(new Date());
        p.setDevolucion(fecha);
        p = actualizarCantidad(p, true);

        return pr.save(p);
    }
    
    
    
    /*              LISTAR Y BUSQUEDA             */
    
    
    public List<Prestamo> listAll(){
        List<Prestamo> list = pr.findAll();
        for (Prestamo p : list) {
            p.setMulta(multa(p.getDevolucion()));
        }
        return list;
    }
    
    public List<Prestamo> findByQuery(String query) {
        Long doc;
        try{
            doc = Long.parseLong(query);   
        } catch (Exception e){
            doc= null;
        }
        return pr.findAll("%"+query+"%", doc);
    }
    
    
    public Optional<Prestamo> findById(String id){
        return pr.findById(id);
    }
    
    
    /*              ELIMINAR             */
    
    
    @Transactional
    public void delete(Prestamo p){
        pr.delete(p);
    }
    
    @Transactional
    public void delete(String id){
        pr.delete(findById(id).get());
    }
    
    
    
    /*              COMPROBAR LIBRO DISPONIBLE             */
    
    public Boolean disponibilidad(Libro libro){
        return libro.getEjemplares() > 0;
    }
    
    
    
    /*              RENOVAR             */
    
    public void renovar(Prestamo prestamo, Date nuevaDevolucion) throws WebException{
        
        if (prestamo.getDevolucion().before(new Date()) || prestamo.getDevolucion().equals(new Date())) {
            prestamo.setDevolucion(nuevaDevolucion);
            ;
        } else {
            prestamo.setMulta(prestamo.getMulta() + multa(prestamo.getDevolucion()));
        }
        prestamo = actualizarCantidad(prestamo, false);
        
        save(prestamo);
    }
    
    
    /*              DEVOLVER             */
    public void devolver(Prestamo prestamo){
//        if(prestamo.getDevolucion().after(new Date())){                 
//            prestamo.setMulta((multa(prestamo.getDevolucion())));
//        }
        
        if((new Date().after(prestamo.getDevolucion()))){
            prestamo.setMulta((multa(prestamo.getDevolucion())));
        }
        
        
        prestamo.setDevolucion(new Date());
        //actualizo cantidad en libro
        //prestamo.setLibro(actualizarCantidad(prestamo, false));
        actualizarCantidad(prestamo, false);
         
    }
    
    
    
    /*              MULTA             */
    
    public void setPrecioMulta(Double nuevoPrecio){
        if(this.precioMulta == null || this.precioMulta == 0d){
            this.precioMulta = 1d;
        } else{
            this.precioMulta = nuevoPrecio;
        }
        
    }
    
    

    public Double multa(Date fechaDevolucion) {
        Date hoy= new Date();
        if(this.precioMulta == null){
            this.precioMulta = 1d;
        } 
//        return diasAtrasados(fechaDevolucion, hoy) * precioMulta;
        return (diasAtrasados(fechaDevolucion, hoy)) > 0 ? (diasAtrasados(fechaDevolucion, hoy) * precioMulta): 0d;
    }
    
    
    
    /*         UTILIDADES           */
    
    public Prestamo actualizarCantidad(Prestamo prestamo, Boolean estado){
        //true: prestar ---- false: devolver
        
        
        
            Libro libro = prestamo.getLibro();
           if(estado){           
               libro.setEjemplares(libro.getEjemplares() -1);
               libro.setPrestados(libro.getPrestados() +1);
           } else{
               libro.setEjemplares(libro.getEjemplares() +1);
               libro.setPrestados(libro.getPrestados() -1);
           }   
            
        prestamo.setLibro(libro);
        return prestamo;
    }
    
    
    public Double diasAtrasados(Date desde, Date hasta){
        // convertimos a dias, para que no afecten cambios de hora
        long diasDesde = (long) Math.floor(desde.getTime() / (1000*60*60*24));
        long diasHasta = (long) Math.floor(hasta.getTime() / (1000*60*60*24));
        return ((double) (diasHasta - diasDesde) - 1d);
    }
    
}
