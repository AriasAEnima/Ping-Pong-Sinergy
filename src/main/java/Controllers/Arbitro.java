/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.ServiciosFisica.Dir;
import Elements.Pelota;
import Elements.Mesa;
import Elements.Raqueta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author J. Eduardo Arias
 */
public class Arbitro {   
    private PelotaController pc;
    private JugadoresController jc;
    
    
    
    public Arbitro(Dir h,Dir v) {
        try {
            Mesa mesa=new Mesa(700,500);
            Pelota pelota=new Pelota(50, 50, mesa);
            Map<String,Raqueta> js=new HashMap<String,Raqueta >();
            js.put("jugador1", new Raqueta(150, 50, 50, 150));
            js.put("jugador2", new Raqueta(600, 250, 50, 150));
            pelota.setDir(h);     
            pelota.setDir(v);
            jc=new JugadoresController(js,mesa);
            pc=new PelotaController(mesa, pelota,js.values()); 
         
        } catch (Exception ex) {
            Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    
    public void continuar(){
        pc.MuevaPelota();
    }   
    
    public String ubicacionPelota(){
        return pc.ubicacionPelota();
    }
    
    public List<String> ubicacionJugadores(){
        return jc.ubicacionJugadores();
    }
    
    public boolean huboContactoPelota(){
        return pc.getReboto();
    }
    
    public void MoverJugador(String name,Dir dir){
        try {
            jc.muevaJugador(name, dir);
        } catch (Exception ex) {
            Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    
}
