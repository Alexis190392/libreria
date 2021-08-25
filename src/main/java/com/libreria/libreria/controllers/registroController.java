package com.libreria.libreria.controllers;

import com.libreria.libreria.excepciones.WebException;
import com.libreria.libreria.services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class registroController {
    
    @Autowired
    private UsuarioServicio us;
    
    @GetMapping("")
    public String registro(){
        return "registro";
    }
    
    @PostMapping("")
    public String registroSave(Model model, @RequestParam String usuario, @RequestParam String password, @RequestParam String password2) throws WebException{
        try{
            us.save(usuario, password, password2);
            return "redirect:/";
        } catch (WebException e){
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}
