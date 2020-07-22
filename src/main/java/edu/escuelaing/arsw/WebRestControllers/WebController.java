/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.WebRestControllers;

/**
 *
 * @author J. Eduardo Arias
 */
import edu.escuelaing.arsw.Persistence.JugadorRepository;
import edu.escuelaing.arsw.entities.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebController {
    @Autowired
    private JugadorRepository repository;
    
    @GetMapping("/status")
    public String status() {
        return "{\"status\":\"Greetings from Spring Boot Ping Pong Synergy "
                + java.time.LocalDate.now() + ", "
                + java.time.LocalTime.now()
                + ". " + "The server is Runnig!\"}";
    }
    
    @GetMapping("/getuser")
    public String getUser() {
        String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(currentUserName);
        return  repository.findByUsername(currentUserName).toJSON().toString();
    }
    
    @PostMapping("/newuser")    
    public String nuevoUsuario(@RequestParam(value = "nick") String nick, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
            Jugador j=null;
            System.out.println("Llego a nuevo usuario");
           if(repository.findByUsername(nick)==null){
              j=new Jugador(nick, email, new BCryptPasswordEncoder().encode(password));
               repository.save(j);
              
           }else{
               j=new Jugador("", "", new BCryptPasswordEncoder().encode("a"));           
           }         
            return j.toJSON().toString();           
    }
            
            

    
}
