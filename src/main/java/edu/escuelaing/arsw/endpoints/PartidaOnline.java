/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.endpoints;

import edu.escuelaing.arsw.Controllers.Arbitro;
import edu.escuelaing.arsw.Controllers.Observer;
import edu.escuelaing.arsw.Controllers.ServiciosFisica;
import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author J. Eduardo Arias
 */
public class PartidaOnline implements Observer{    
    private Session[] jugadores;

    private List<Session> espectadores;
    private Arbitro arbitro;
    private Thread h;
  
    private static final Logger logger = Logger.getLogger(PartidaOnline.class.getName());

    
    public void processPlayerMove(String jugador, Dir D,Session session){
        if (session == jugadores[0] || session == jugadores[1]) {
            arbitro.MoverJugador(jugador, D);
            notifyChangue();
        }
      
    }
    
    
    public PartidaOnline(){
        jugadores=new Session[]{null,null};
        espectadores=new ArrayList<>();        
    }
    
    public void addPlayer(Session np) {
        try {
            String player = "";
            if (jugadores[0] == null && jugadores[1] == null) {
                arbitro = new Arbitro(this);
                arbitro.PreparePartida(ServiciosFisica.Dir.UP, ServiciosFisica.Dir.RIGTH, 2);
                jugadores[0] = np;
                player = "jugador1";                
            } else if (jugadores[0] == null) {
                jugadores[0] = np;
                player = "jugador1";  
            } else if (jugadores[1] == null) {
                jugadores[1] = np;
                player = "jugador2";  
                if(h==null){
                    h = new Thread(arbitro);
                    h.start();
                }               
            } else {
                player="espectador";
                espectadores.add(np);
            }
            np.getBasicRemote().sendText("Connection established.");
            np.getBasicRemote().sendText("{\"you\":\""+player+"\"}");
            notifyChangue();
        } catch (IOException ex) {
            Logger.getLogger(PartidaOnline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PartidaOnline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void notifyChangue() {       
        send(arbitro.ElementosToJson());           
        if(arbitro.termino()){
            send("{\"termino\": true }");
        }
    }
    
        
    public void send(String msg) {
        try {
            /* Send updates to all open WebSocket sessions */
            synchronized (this) {               
                for (int i = 0; i < jugadores.length; i++) {
                    if (jugadores[i] != null && jugadores[i].isOpen()) {
                        jugadores[i].getBasicRemote().sendText(msg);
                    }
                }                
                for (Session e : espectadores) {
                    if(e.isOpen()){
                        e.getBasicRemote().sendText(msg);
                    }                    
                }                
            }         

        } catch (IOException e) {
            logger.log(Level.INFO, e.toString() + " Error aqui ");
        }
    }

    public void removePlayer(Session session) {
        synchronized(this){
            if(jugadores[0]==session){
                jugadores[0]=null;
            }else if(jugadores[1]==session){
                jugadores[1]=null;
            }else if(espectadores.contains(session)){
                espectadores.remove(session);
            }
            if(h!=null && !existenJugadores()){
                arbitro.pare();
            }
        }
    }
    
    public boolean existenJugadores(){
        return !(jugadores[0]==null && jugadores[1]==null);
    }
    
   
    
}
