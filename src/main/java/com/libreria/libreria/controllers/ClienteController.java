package com.libreria.libreria.controllers;

import com.libreria.libreria.services.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente") //el principal del controller 
public class ClienteController {

    @Autowired
    private ClienteServicio cs;
    

    @GetMapping("/list") //metodos del controller
    public String listarClientes(Model m) {
        m.addAttribute("clientes", cs.listAll());
        return "administrarClientes";
    }

    @GetMapping("/form")
    public String crearCliente() {
        return "crearCliente";
    }

    @PostMapping("/save")
    public String guardarCliente(@RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String domicilio, @RequestParam String tel) {
        cs.save(dni, nombre, apellido, domicilio, tel);
            return "redirect:/cliente/list";
    }
    

    
    @GetMapping("/delete")
    public String eliminarCliente(@RequestParam(required=true) Long dni){
    cs.deleteById(dni);
    return"redirect:/cliente/list";
    }
}
