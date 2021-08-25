package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Usuario;
import com.libreria.libreria.excepciones.WebException;
import com.libreria.libreria.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepository ur;
    
    public Usuario save(String usuario, String password, String password2) throws WebException{
        Usuario u = new Usuario();
        
        if(usuario == null || usuario.isEmpty()){
            throw new WebException("El usuario no puede estar vacio, no seas IDIOTA!");
        }
        
        if(password == null || password2 == null || password.isEmpty() || password2.isEmpty()){
            throw new WebException("La contraseña no puede estar vacia. USTED NO APRENDE VERDAD?");
        }
        
        if (!password.equals(password2)){
            throw new WebException("Las contraseñas no son iguales. Definitivamente es un IDIOTA!");
        }
        
        u.setUser(usuario);
        u.setPassword(password);
        
        return ur.save(u);
    }
}
