//package com.libreria.libreria.controllers;
//
//import com.libreria.libreria.services.ClienteServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/cliente") //el principal del controller 
//public class ClienteController {
//
//    @Autowired
//    private ClienteServicio cs;
//    
//
//    @GetMapping("/list") //metodos del controller
//    public String listarClientes(Model m) {
//        m.addAttribute("clientes", cs.listAll());
//        return "administrarClientes";
//    }
//
//    @GetMapping("/form")
//    public String crearCliente() {
//        return "crearCliente";
//    }
//
//    @PostMapping("/save")
//    public String guardarCliente(@RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String domicilio, @RequestParam String telefono) {
//        cs.save(documento, nombre, apellido, domicilio, telefono);
//            return "redirect:/cliente/list";
//    }
//    
//
//    
//    @GetMapping("/delete")
//    public String eliminarCliente(@RequestParam(required=true) Long documento){
//    cs.deleteById(documento);
//    return"redirect:/cliente/list";
//    }

package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Cliente;
import com.libreria.libreria.services.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteServicio cs;
    
    @GetMapping("/form")
    public String crearCliente(Model model, @RequestParam(required= false) String documento){
        if(documento != null){
            Cliente l = cs.findById(Long.parseLong(documento));
            if(l != null){
                model.addAttribute("cliente", l);
            } else{
                return "redirect:/cliente/list";
            }
        } else{
            model.addAttribute("cliente",new Cliente());
        }
        return "crearCliente";
    }
    
    @GetMapping("/list")
    public String listarClientes(Model model, @RequestParam(required= false) String query){
        if(query != null){
            model.addAttribute("clientes", cs.findByQuery(query));
        } else {
            model.addAttribute("clientes", cs.listAll());
        }
        return "administrarClientes";
    }
    
    @PostMapping("/save")
    public String guardarCliente(RedirectAttributes redat, @ModelAttribute Cliente cliente){
        try {
            cs.save(cliente);
        } catch (Exception e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
        }
        return "redirect:/cliente/list";
    }
    
    //para eliminar
    @GetMapping("/delete")
    public String eliminarCliente(@RequestParam(required=true) Long documento){
        cs.deleteByDocumento(documento); //desde cliente servicio permite la eliminacion
        return "redirect:/cliente/list";
    }
}
