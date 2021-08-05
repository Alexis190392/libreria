package com.libreria.libreria.controllers;

import com.libreria.libreria.entidades.Editorial;
import com.libreria.libreria.services.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {
    @Autowired
    private EditorialServicio as;
    
    @GetMapping("/list")
    public String listarEditoriales(Model model, @RequestParam(required=false) String query){
        if(query != null){
            model.addAttribute("editoriales", as.listByQuery(query));
        } else {
            model.addAttribute("editoriales", as.listAll());
        }
        return "administrarEditoriales";
    }
    
    @GetMapping("/form")
    public String crearEditorial(Model model,@RequestParam(required=false) String id){
        if(id != null){
            Optional<Editorial> optional = as.searchId(id);
            if(optional.isPresent()){
                model.addAttribute("editorial", optional.get());
            } else {
                return "redirect:/editorial/list";
            }
        }else{
            model.addAttribute("editorial", new Editorial());
        }
        return "crearEditorial";
    }
    
    
    @PostMapping("/save")
    public String guardarEditorial(RedirectAttributes redat, @ModelAttribute Editorial editorial){
        try {
            as.save(editorial);
        } catch (Exception e) {
            redat.addFlashAttribute("error", e.getMessage()); //mandando el mensaje de error a donde es redireccionado
        }
        return "redirect:/editorial/list";
    }
    
    //para eliminar
    @GetMapping("/delete")
    public String eliminarEditorial(@RequestParam(required=true) String id){
        as.deleteById(id);
        return "redirect:/editorial/list";
    }
}