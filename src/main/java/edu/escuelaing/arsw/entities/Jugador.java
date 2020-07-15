/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.entities;

import java.util.List;
import org.json.JSONObject;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 *
 * @author J. Eduardo Arias
 */
public class Jugador{
    public String username;
    public String correo;
    public String password;

    public Jugador() {
        
    }

    public Jugador(String username, String correo, String password) {
        this.username = username;
        this.correo = correo;
        this.password = password;
    }    

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Jugador [ \"nick\"= \"" + username + "\",\"correo\"=" + correo + "]";

    }

    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        jo.put("nick", username);
        jo.put("correo", correo);
        return jo;
    }

}
