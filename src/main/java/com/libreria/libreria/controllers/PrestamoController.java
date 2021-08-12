package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Prestamo;
import com.libreria.libreria.services.ClienteServicio;
import com.libreria.libreria.services.LibroServicio;
import com.libreria.libreria.services.PrestamoServicio;
import java.util.Optional;
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
@RequestMapping("/prestamo")
public class PrestamoController {
    
    @Autowired
    private PrestamoServicio ps;
    
    //Faltaba agregar los servicios de clientes y libros para poder verlos y usarlos en el front
    //por esto nos daba primero que faltaba o no detectaba a cliente en el front, despues de unir al servicio
    // se detecta mismo problema con libro
    @Autowired
    private ClienteServicio cs;
    @Autowired
    private LibroServicio ls;
    
    
    // formulario para crear prestamo
    @GetMapping("/form")
    public String crearPrestamo(Model model, @RequestParam(required= false) String id
           /* @RequestParam String libro, 
            @RequestParam Long dni, 
            @RequestParam Date devolucion */ ){
       
        if(id!=null){
            Optional< Prestamo> op = ps.findById(id);
        
            if(op.isPresent()){
                model.addAttribute("prestamo", op.get());
            } else {
                return "redirect:/prestamo/list";
            }
            
        } else {
            model.addAttribute("prestamo", new Prestamo());
        }
        model.addAttribute("clientes", cs.listAll());
        model.addAttribute("libros", ls.listAll());
        
       return "crearPrestamo"; //o return "prestamos.html" -> indica a que archivo html debe ir
    }
    
    //registrar prestamo a traves del form // esto era lo que nos faltaba
    
    @PostMapping("/registrar")
    public String registrar(RedirectAttributes redat, @ModelAttribute Prestamo p){
        try{
            ps.registrar(p); //desde aca lo guardamos a traves del servicio lo que nos traiga del formulario
            redat.addFlashAttribute("succes", "Prestamo creado con exito!"); //para el alert
        }catch (Exception e){
            redat.addAttribute("error", e.getMessage());
        }
        return "redirect:/prestamo/list";
    }
    
    //listado de prestamos
    
    @GetMapping("/list")
    public String listadoPrestamos(Model model, @RequestParam(required = false) String consulta){
        if(consulta != null){                                       //valido para poder colocar el buscador algo
            model.addAttribute("prestamos", ps.findById(consulta));
        } else{                                                      // caso de que no coloque nada, me muestra todo
            model.addAttribute("prestamos", ps.listAll());
        }
        return "administrarPrestamo";
    }
    
}
