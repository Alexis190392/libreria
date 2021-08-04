package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Autor;
import com.libreria.libreria.services.AutorServicio;
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
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorServicio as;
    
    @GetMapping("/list")
    public String listarAutores(Model model, @RequestParam(required=false) String query){
        if(query != null){
            model.addAttribute("autores", as.listByQuery(query));
        } else {
            model.addAttribute("autores", as.listAll());
        }
        return "administrarAutores";
    }
    
    @GetMapping("/form")
    public String crearAutor(Model model,@RequestParam(required=false) String id){
        if(id != null){
            Optional<Autor> optional = as.searchId(id);
            if(optional.isPresent()){
                model.addAttribute("autor", optional.get());
            } else {
                return "redirect:/autor/list";
            }
        }else{
            model.addAttribute("autor", new Autor());
        }
        return "crearAutor";
    }
    
    
    @PostMapping("/save")
    public String guardarAutor(RedirectAttributes redat, @ModelAttribute Autor autor){
        try {
            as.save(autor);
        } catch (Exception e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
        }
        return "redirect:/autor/list";
    }
    
    //para eliminar
    @GetMapping("/delete")
    public String eliminarAutor(@RequestParam(required=true) String id){
        as.deleteById(id);
        return "redirect:/autor/list";
    }
}