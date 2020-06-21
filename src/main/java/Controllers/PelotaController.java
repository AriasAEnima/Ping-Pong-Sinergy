/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.ServiciosFisica.Dir;
import Elements.Mesa;
import Elements.Pelota;
import Elements.Raqueta;
import java.util.Collection;

/**
 *
 * @author J. Eduardo Arias
 */
public class PelotaController {   
    private final Mesa mesa;
    private final Pelota pelota;
    private boolean reboto;
    private final Collection<Raqueta> jugadores;

    public PelotaController( Mesa mesa, Pelota pelota,Collection<Raqueta> jugadores) {      
        this.mesa = mesa;
        this.pelota = pelota;
        this.jugadores =jugadores;
    }
            
    
    public void MuevaPelota(){
        Collection<Dir> newDirs=ServiciosFisica.nextDirPelota(mesa, pelota,jugadores);
        if(newDirs!=null){
            System.out.println(newDirs);
            for(Dir newDir:newDirs){
                pelota.setDir(newDir);
            }            
            reboto=true;
        }else{
            reboto=false;
        }
        pelota.move();
    }

    public boolean getReboto(){
        return reboto;
    }

    public String ubicacionPelota() {
        return pelota.toString();
    }
    
}
