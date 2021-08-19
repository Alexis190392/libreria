package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Prestamo;
import com.libreria.libreria.excepciones.WebException;
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
    @Autowired
    private ClienteServicio cs;
    @Autowired
    private LibroServicio ls;
    
    @GetMapping("/list")
    public String listar(Model model, @RequestParam(required=false) String query){
        if(query != null){
            model.addAttribute("prestamos", ps.findByQuery(query));
        } else{
            model.addAttribute("prestamos", ps.listAll());
        }
        return "administrarPrestamo"; 
    }
    
    @GetMapping("/form")
    public String crearPrestamo(Model model, @RequestParam(required=false) String id){
        if(id != null){
            Optional<Prestamo> p = ps.findById(id);
            if(p.isPresent()){
                model.addAttribute("prestamo", p);
            } else {
                return "redirect:/prestamo/list";
            }
        } else{
            model.addAttribute("prestamo", new Prestamo());
        }
        model.addAttribute("clientes", cs.listAll());
        model.addAttribute("libros", ls.listAll());
        
        return "crearPrestamo";
    }
    
    
    @PostMapping("/save")
    public String hacerPrestamo(RedirectAttributes redat, @ModelAttribute Prestamo p){
        try{
            ps.save(p);
            redat.addFlashAttribute("success", "Prestamo creado con exito.");
        } catch(WebException e){
            redat.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/prestamo/list";
    }
    
    @GetMapping("/delete")
    public String eliminarPrestamo(@RequestParam(required=true) String id){
        ps.delete(id);
        return "redirect:/prestamo/list";
    }

}
