package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.repositories.ClienteRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepository cr;

    //guardar
    @Transactional
    public Cliente save(Cliente cliente) {
        return cr.save(cliente);
    }

    @Transactional
    public Cliente save(Long dni, String nombre, String apellido, String domicilio, String telefono) {
        Cliente c = new Cliente();
        c.setDocumento(dni);
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setDomicilio(domicilio);
        c.setTelefono(telefono);
        return cr.save(c);
    }

    //eliminar
    public void delete(Cliente cliente) {
        cr.delete(cliente);
    }

    //eliminar by id
    public void deleteById(Long dni) {
        Optional<Cliente> op = cr.findById(dni);
        if (op.isPresent()) {
            cr.delete(op.get());
        }
    }

    //listar 
    public List<Cliente> listAll() {
        return cr.findAll();
    }

    //buscar
    public List<Cliente> findByQuery(String query) {

        Long dni;
        try {
            dni = Long.parseLong(query);
        } catch (Exception e) {
            dni = null;
        }

        return cr.findByQuery("%" + query + "%", dni);
    }

    //buscar por ID
    public Cliente findById(Long dni) {
        Cliente cliente = null;
        Optional<Cliente> op = cr.findById(dni);
        if (op.isPresent()) {
            cliente = op.get();
        }
        return cliente;
    }
}
