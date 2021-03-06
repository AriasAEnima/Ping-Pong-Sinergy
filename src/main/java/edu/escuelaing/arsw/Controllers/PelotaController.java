/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Controllers;

import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import edu.escuelaing.arsw.Elements.Mesa;
import edu.escuelaing.arsw.Elements.Pelota;
import edu.escuelaing.arsw.Elements.Raqueta;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author J. Eduardo Arias
 */
public class PelotaController {   
    private final Mesa mesa;
    private final Pelota pelota;
    private boolean reboto;
    private final Collection<Raqueta> jugadores;
    private final Arbitro ar;

    public PelotaController(Arbitro ar, Mesa mesa, Pelota pelota,Collection<Raqueta> jugadores) {      
        this.mesa = mesa;
        this.pelota = pelota;
        this.jugadores =jugadores;
        this.ar=ar;
    }
            
    
    public synchronized void MuevaPelota(){
        pelota.move();
        Collection<Dir> newDirs=ServiciosFisica.nextDirPelota(ar,mesa, pelota,jugadores);
        if(newDirs!=null){
            //System.out.println(pelota.ubicacion());
            //System.out.println(newDirs);
            for(Dir newDir:newDirs){
                pelota.setDir(newDir);
            }            
            reboto=true;
        }else{
            reboto=false;
        }       
    }

    public synchronized boolean getReboto(){
        return reboto;
    }

    public synchronized Point ubicacionPelota() {
        return pelota.ubicacion();
    }
    
    public synchronized String PelotatoJSONString() {     
        String ans="\"pelota\": [";
        ans+=pelota.toJSON().toString();
        ans+="]";
        return ans;
        
    }

    public void reiniciarPelota(Dir dirh,Dir dirv) {
        pelota.setDir(dirh);
        pelota.setDir(dirv);
        pelota.setRelative(mesa);
    }
    
    

  
}
