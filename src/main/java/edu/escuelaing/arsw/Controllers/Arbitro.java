/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Controllers;

import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import edu.escuelaing.arsw.Elements.Pelota;
import edu.escuelaing.arsw.Elements.Mesa;
import edu.escuelaing.arsw.Elements.Raqueta;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J. Eduardo Arias
 */
public class Arbitro implements Runnable, Observer {

    private PelotaController pc;
    private JugadoresController jc;
    private boolean bandera = true;
    private Observer ob;

    public Arbitro(){}
    
    public Arbitro(Observer ob) {
        this.ob = ob;
    }

    public void PreparePartida(Dir h, Dir v) throws Exception {
        Mesa mesa = new Mesa(700, 500);
        Pelota pelota = new Pelota(50, 50, mesa);
        pelota.setDir(h);
        pelota.setDir(v);
        jc = new JugadoresController(mesa,this);
        pc = new PelotaController(this, mesa, pelota, jc.getRaquetas());
    }

    public void PreparePartida(Dir h, Dir v, int njug) throws Exception {
        Mesa mesa = new Mesa(700, 500);
        Pelota pelota = new Pelota(50, 50, mesa);
        pelota.setDir(h);
        pelota.setDir(v);
        jc = new JugadoresController(mesa, njug);
        pc = new PelotaController(this, mesa, pelota, jc.getRaquetas());
    }

    public void continuar() {
        pc.MuevaPelota();
        notifyChangue();
    }

    public Point ubicacionPelota() {
        return pc.ubicacionPelota();
    }

    public List<Point> ubicacionJugadores() {
        return jc.ubicacionJugadores();
    }
    
    public synchronized String ElementosToJson() {
        boolean primero=true;
        String ans="{"+pc.PelotatoJSONString()+","+jc.JugadorestoJSONString()+"}";       
    
      
        return ans;
        
    }

    public boolean huboContactoPelota() {
        return pc.getReboto();
    }

    public void MoverJugador(String name, Dir dir) {
        try {
            jc.muevaJugador(name, dir);
        } catch (Exception ex) {
            Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void notificarReboteEnJugador(Raqueta r) {
        String name = jc.getJugadorPorRaqueta(r);
        System.out.println("Ha respondido : " + name);
    }

    @Override
    public void run() {
        while (bandera) {
            try {
                Thread.sleep(8);
                continuar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Arbitro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void pare() {
        bandera = false;
    }

    @Override
    public void notifyChangue() {
        if (ob != null) {
            ob.notifyChangue();
        }

    }

}
