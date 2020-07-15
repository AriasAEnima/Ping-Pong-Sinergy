/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw;

import edu.escuelaing.arsw.Persistence.JugadorRepository;
import edu.escuelaing.arsw.entities.Jugador;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebStartApp implements CommandLineRunner{
    @Autowired
    private JugadorRepository repository;
    
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(WebStartApp.class);
        app.setDefaultProperties(Collections.singletonMap("spring.data.mongodb.uri",
                "mongodb+srv://eduardo:eduardo@mongodbtest.bpng2.mongodb.net/PingPongSynergy"));
        app.run(args);
    }
    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        // save a couple of customers
        repository.save(new Jugador("Eduardo", "eduardo@gmail.com", new BCryptPasswordEncoder().encode("miclave")));
       

    }
}