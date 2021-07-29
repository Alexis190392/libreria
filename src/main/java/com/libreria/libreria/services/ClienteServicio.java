package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.repositories.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepository cr;
    
    public List<Cliente> listAll(){
        return cr.findAll();
    }
    
    public Cliente save(Cliente c){
        return cr.save(c);
    }
    
    public Cliente save(Long dni, String nombre, String apellido, String domicilio, String tel){
        Cliente c = new Cliente();
        c.setDocumento(dni);
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setDomicilio(domicilio);
        c.setTelefono(tel);
        return cr.save(c);
    }
    
    public void delete(Cliente c){
        if(cr.findById(c.getDocumento().toString()).isPresent()){
            cr.delete(c);
        }
    }
}
