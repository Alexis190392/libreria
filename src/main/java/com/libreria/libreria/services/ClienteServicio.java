package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.repositories.ClienteRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class ClienteServicio {
//
//    @Autowired
//    private ClienteRepository cr;
//
//    //guardar
//    @Transactional
//    public Cliente save(Cliente cliente) {
//        return cr.save(cliente);
//    }
//
//    @Transactional
//    public Cliente save(Long documento, String nombre, String apellido, String domicilio, String telefono) {
//        Cliente c = new Cliente();
//        c.setDocumento(documento);
//        c.setNombre(nombre);
//        c.setApellido(apellido);
//        c.setDomicilio(domicilio);
//        c.setTelefono(telefono);
//        return cr.save(c);
//    }
//
//    //eliminar
//    public void delete(Cliente cliente) {
//        cr.delete(cliente);
//    }
//
//    //eliminar by id
//    public void deleteById(Long documento) {
//        Optional<Cliente> op = cr.findById(documento);
//        if (op.isPresent()) {
//            cr.delete(op.get());
//        }
//    }
//
//    //listar 
//    public List<Cliente> listAll() {
//        return cr.findAll();
//    }
//
//    //buscar
//    public List<Cliente> findByQuery(String query) {
//
//        Long documento;
//        try {
//            documento = Long.parseLong(query);
//        } catch (Exception e) {
//            documento = null;
//        }
//
//        return cr.findByQuery("%" + query + "%", documento);
//    }
//
//    //buscar por ID
//    public Cliente findById(Long documento) {
//        Cliente cliente = null;
//        Optional<Cliente> op = cr.findById(documento);
//        if (op.isPresent()) {
//            cliente = op.get();
//        }
//        return cliente;
//    }
//}

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepository cr;
//    @Autowired
//    private AutorServicio as;
//    @Autowired
//    private EditorialServicio es;
    
    
    
    //guardar
    @Transactional
    public Cliente save(Cliente cliente){
        return cr.save(cliente);
    }
    
  @Transactional
    public Cliente save(Long documento, String nombre, String apellido, String domicilio, String telefono) {
        Cliente c = new Cliente();
        c.setDocumento(documento);
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setDomicilio(domicilio);
        c.setTelefono(telefono);
        return cr.save(c);
    }
    
    
    public List<Cliente> listAll(){
        return cr.findAll(); 
    }
    
    //buscador general de clientes en la db
    public List<Cliente> findByQuery(String query){
        
        Long documento;
        try{
            documento = Long.parseLong(query);   
        } catch (Exception e){
            documento= null;
        }
        return cr.findByQuery("%"+query+"%", documento);
    }
    
    
    public Cliente findById(Long documento){
        Cliente cliente= null;
        Optional<Cliente> op = cr.findById(documento);
        if(op.isPresent()){
            cliente = op.get();
        }
        return cliente;
    }
    
    public void delete (Cliente cliente){
        cr.delete(cliente);
    }
    
    public void deleteByDocumento(Long documento){
        Optional<Cliente> op = cr.findById(documento);
        if(op.isPresent()){
            cr.delete(op.get()); //nos trae como objeto para eliminarlo
        }
    }

}
