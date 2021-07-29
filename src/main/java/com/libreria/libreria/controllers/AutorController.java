/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreria.controllers;

import com.libreria.libreria.services.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorServicio as;
    
    @GetMapping("/list")
    public String listarAutores(Model model){
        model.addAttribute("autores", as.listAll());
        return "administrarAutores";
    }
    
    @GetMapping("/form")
    public String crearAutor(){
        return "crearAutor";
    }
    
    @PostMapping("/save")
    public String guardarAutor(@RequestParam String nombre){
        as.save(nombre);
        return "redirect:/autor/list"; 
    }
}
