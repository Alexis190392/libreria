package com.libreria.libreria.services;

import com.libreria.libreria.entidades.Personal;
import com.libreria.libreria.entidades.Usuario;
import com.libreria.libreria.excepciones.WebException;
import com.libreria.libreria.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepository ur;
    
//    public Usuario save(String usuario, String password, String password2) throws WebException{
//        Usuario u = new Usuario();
//        
//        if(usuario == null || usuario.isEmpty()){
//            throw new WebException("El usuario no puede estar vacio, no seas IDIOTA!");
//        }
//        
//        if(password == null || password2 == null || password.isEmpty() || password2.isEmpty()){
//            throw new WebException("La contraseña no puede estar vacia. USTED NO APRENDE VERDAD?");
//        }
//        
//        if (!password.equals(password2)){
//            throw new WebException("Las contraseñas no son iguales. Definitivamente es un IDIOTA!");
//        }
//        
//        u.setUser(usuario);
//        u.setPassword(password);
//        
//        return ur.save(u);
//    }
    
    public Personal save(Long documento, String nombre, String apellido, String domicilio, String telefono, String cargo, 
                        String usuario, String password, String password2) throws WebException{
        //Personal p = new Personal();
         Usuario u = new Usuario();  //ya que extiende de personal
         
         
        if(documento == null || documento <= 9999999){
            throw new WebException("El documento no puede ser nulo o es incorrecto, no seas IDIOTA!");
        }
        
        if(nombre == null || nombre.isEmpty()){
            throw new WebException("El nombre no puede estar vacio , no seas IDIOTA!");
        }
        
        if(apellido == null || apellido.isEmpty()){
            throw new WebException("El apellido no puede estar vacio , no seas IDIOTA!");
        }
        
        if(domicilio == null || domicilio.isEmpty()){
            throw new WebException("El domicilio no puede estar vacio , no seas IDIOTA!");
        }
        
        if(telefono == null || telefono.isEmpty()){
            throw new WebException("El telefono no puede estar vacio , no seas IDIOTA!");
        }
        
        if(cargo == null || cargo.isEmpty()){
            throw new WebException("El cargo no puede estar vacio , no seas IDIOTA!");
        }
        
//        p.setNombre(nombre);
//        p.setApellido(apellido);
//        p.setDocumento(documento);
//        p.setTelefono(telefono);
//        p.setCargo(cargo);
        
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setDocumento(documento);
        u.setTelefono(telefono);
        u.setCargo(cargo);
        
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
