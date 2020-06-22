/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Elements.Mesa;
import Elements.Pelota;
import Elements.Raqueta;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 *
 * @author J. Eduardo Arias
 */
public class ServiciosFisica {
  
    
    public enum Dir {
        LEFT,
        RIGTH, 
        UP,
        DOWN,
        NONEHORIZONTAL,
        NONEVERTICAL
    }      

    
    public static boolean puedeMoverJugador(Mesa mesa,Raqueta jugador,Dir dir,Collection<Raqueta> jugadores ){
        Map<Dir,Rectangle> colJug=jugador.getColisiones();
        if(dirCol(colJug,mesa.getColisiones()).contains(dir)){
            return false;
        }
        for(Raqueta j: jugadores){
            if(j!=jugador){
                if(dirCol(colJug,j.getColisiones()).contains(dir)){
                    return false;
                }
            }
        }
        
       return true;
    }
    
    public static Collection<Dir> nextDirPelota(Arbitro ar,Mesa mesa,Pelota pelota,Collection<Raqueta> jugadores){ 
        Map<Dir,Rectangle> ladospelota=pelota.getColisiones();
        Collection<Dir> ans=swapDirPelota(ladospelota,mesa.getColisiones());
        if(ans.isEmpty()){           
            for (Raqueta r: jugadores){
                ans= swapDirPelota(ladospelota,r.getColisiones());
                if(!ans.isEmpty()){      
                    ar.notificarReboteEnJugador(r);
                    return ans;
                }
            }        
            return null;
        }else{
            return ans;
        }               
    }
    
    private static Collection<Dir> swapDirPelota(Map<Dir,Rectangle> objetivo,Map<Dir,Rectangle> ans){
        List<Dir> dirrc=dirCol(objetivo,ans);    
        for (int i=0;i<dirrc.size();i++){
            switch (dirrc.get(i)) {
                case UP:
                     dirrc.set(i, Dir.DOWN);
                    break;
                case DOWN:
                    dirrc.set(i, Dir.UP);
                    break;
                case LEFT:
                     dirrc.set(i, Dir.RIGTH);                   
                    break;
                case RIGTH:
                     dirrc.set(i, Dir.LEFT);      
                    break;
            }
        
        }
        return dirrc;
    }
    
    private static List<Dir> dirCol(Map<Dir,Rectangle> objetivo,Map<Dir,Rectangle> others){
        List<Dir> ans=new ArrayList<>();
        for(Dir dir:objetivo.keySet()){
            for (Rectangle rans:others.values()){                
                    if (objetivo.get(dir).intersects(rans)) {
                        ans.add(dir);
                    }                
                }
        }
        return ans;
    }
    
        

}
