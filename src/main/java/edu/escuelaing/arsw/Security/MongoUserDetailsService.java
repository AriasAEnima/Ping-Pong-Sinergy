/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Security;

import edu.escuelaing.arsw.Persistence.JugadorRepository;
import edu.escuelaing.arsw.entities.Jugador;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author J. Eduardo Arias
 */
@Component
public class MongoUserDetailsService implements UserDetailsService{

    @Autowired
    private JugadorRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          System.out.println( "Se llamo a load");
        Jugador user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("usuario no encontrado");
            throw new UsernameNotFoundException("User not found");
          
        }else{
            System.out.println(user);
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}