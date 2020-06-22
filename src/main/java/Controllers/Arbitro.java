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
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author J. Eduardo Arias
 */
public class Arbitro implements Runnable{   
    private PelotaController pc;
    private JugadoresController jc;
    private boolean bandera=true;
    
       
    public void IniciePartida(Dir h,Dir v) throws Exception{               
            Mesa mesa=new Mesa(700,500);     
            Pelota pelota=new Pelota(50, 50, mesa);
            pelota.setDir(h);     
            pelota.setDir(v);    
            jc=new JugadoresController(mesa);
            pc=new PelotaController(this,mesa, pelota,jc.getRaquetas()); 
    }
    
    
    public void continuar(){
        pc.MuevaPelota();
    }   
    
    public Point ubicacionPelota(){
        return pc.ubicacionPelota();
    }
    
    public List<Point> ubicacionJugadores(){
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

    public void notificarReboteEnJugador(Raqueta r) {
        String name=jc.getJugadorPorRaqueta(r);
        System.out.println("Ha respondido : "+name);
    }

    @Override
    public void run() {
        while(bandera){
            try {
                Thread.sleep(10);
                continuar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stop(){
        bandera=false;
    }
  
    
}
