package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.entidades.Libro;
import com.libreria.libreria.services.LibroServicio;
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
@RequestMapping("/libro")
public class LibroController {
    
    @Autowired
    private LibroServicio ls;
    
    @GetMapping("/form")
    public String crearLibro(Model model, @RequestParam(required= false) Long isbn){
        if(isbn != null){
            Optional<Libro> op = ls.findByIsbn(isbn);
            if(op.isPresent()){
                model.addAttribute("libro", op.get());
            } else{
                return "redirect:/libro/list";
            }
        } else{
            model.addAttribute("libro",new Libro());
        }
        return "crearLibro";
    }
    
//    @GetMapping("/list")
//    public String listarLibros(Model model){
//        model.addAttribute("libros", ls.listAll());
//        return "administrarLibros";
//    }
    
    @GetMapping("/list")
    public String listarLibros(Model model, @RequestParam(required= false) String query){
        if(query != null){
            model.addAttribute("libros", ls.findByQuery(query));
        } else {
            model.addAttribute("libros", ls.listAll());
        }
        return "administrarLibros";
    }
    
    
    
//    @GetMapping("/form")
//    public String crearLibro(){
//        return "crearLibro";
//    }
    
//    @PostMapping("/save")
//    public String guardarLibro(@RequestParam Long isbn, @RequestParam String titulo,@RequestParam Integer anio,@RequestParam Integer ejemplares,@RequestParam Integer prestados,@RequestParam String autor,@RequestParam String editorial){
//        ls.save(isbn,titulo,anio,ejemplares,prestados,autor,editorial);
//        return "redirect:/libro/list"; 
//    }
    
    @PostMapping("/save")
    public String guardarLibro(RedirectAttributes redat, @ModelAttribute Libro libro){
        try {
            ls.save(libro);
        } catch (Exception e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
        }
        return "redirect:/libro/list";
    }
    
    //para eliminar
    @GetMapping("/delete")
    public String eliminarLibro(@RequestParam(required=true) Long isbn){
        ls.deleteByIsbn(isbn); //desde libro servicio permite la eliminacion
        return "redirect:/libro/list";
    }
    
}
