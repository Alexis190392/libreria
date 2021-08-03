package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroController {
    
    @Autowired
    private LibroServicio ls;
    
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
    
    @GetMapping("/form")
    public String crearLibro(){
        return "crearLibro";
    }
    
    @PostMapping("/save")
    public String guardarLibro(@RequestParam Long isbn, @RequestParam String titulo,@RequestParam Integer anio,@RequestParam Integer ejemplares,@RequestParam Integer prestados,@RequestParam String autor,@RequestParam String editorial){
        ls.save(isbn,titulo,anio,ejemplares,prestados,autor,editorial);
        return "redirect:/libro/list"; 
    }
}
