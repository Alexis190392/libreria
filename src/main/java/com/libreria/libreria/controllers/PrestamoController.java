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
    
    
    

}
