/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.entities;

import java.util.Arrays;
import org.json.JSONObject;



/**
 *
 * @author J. Eduardo Arias
 */
public class Partida {
 
  public String id;
  public Jugador[] jugs;
  public int[] puntuacion;
  
  
  public Partida() {}  


  public Partida(String id,Jugador[] jugs, int[] puntuacion) {
    this.id = id;
    this.jugs =jugs;
    this.puntuacion=puntuacion;
  }

  @Override
  public String toString() {
    return "Partida [ \"id\"= \""+id +"\",\"Jugadores\"="+Arrays.toString(jugs)+"]";
      
  }
  
 public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("jugs", jugs);
        return jo;
    }

}
