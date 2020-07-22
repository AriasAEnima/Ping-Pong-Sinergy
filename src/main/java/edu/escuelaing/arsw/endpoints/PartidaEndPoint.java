/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.endpoints;

/**
 *
 * @author J. Eduardo Arias
 */
import edu.escuelaing.arsw.Controllers.ServiciosFisica;
import java.util.logging.Level;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


@Component
@ServerEndpoint("/partidaService")
public class PartidaEndPoint {


    private static final Logger logger = Logger.getLogger(PartidaEndPoint.class.getName());
    /* Queue for all open WebSocket sessions */ 
   
    
    public static ConcurrentHashMap<String,PartidaOnline> partidasOnline=new ConcurrentHashMap<String, PartidaOnline>();
    
    
    
    private String partida="";
   


    @OnMessage
    public synchronized void processMove(String message, Session session) {
        //logger.log(Level.INFO, "Move received:" + message + ". From session: " + session);
        JSONObject json = new JSONObject(message);
        if(json.has("sala")){
           int sala=json.getInt("sala");
           partida=sala+"";
           entrarPartida(session);
        }
        if(json.has("jugador")){
           String jugador=json.getString("jugador");
           String dir=json.getString("DIR");
           ServiciosFisica.Dir D= ServiciosFisica.Dir.valueOf(dir);         
           partidasOnline.get(partida).processPlayerMove(jugador, D,session);         
        }      
        
    }
    
    private void entrarPartida(Session session){
        try {        
            if(partidasOnline.get(partida)==null){
                partidasOnline.put(partida,new PartidaOnline());
                partidasOnline.get(partida).addPlayer(session);                                      
            }else{
                partidasOnline.get(partida).addPlayer(session);               
            }                
        } catch (Exception ex) {
            Logger.getLogger(PartidaEndPoint.class.getName()).log(Level.SEVERE, null, ex);          
        }        
    }

    @OnOpen
    public void openConnection(Session session) {
        /* Register this connection in the queue */           
        logger.log(Level.INFO, "Connection opened.");
    }


    @OnClose
    public void closedConnection(Session session) {
            
        if (partidasOnline.get(partida)!= null) {
            partidasOnline.get(partida).removePlayer(session);
            if(!partidasOnline.get(partida).existenJugadores()){
                partidasOnline.remove(partida);
            }
        } 
        logger.log(Level.INFO, "Connection closed for session " + session);       
    }


    @OnError
    public void error(Session session, Throwable t) {
        if (partidasOnline.get(partida)!= null) {
            partidasOnline.get(partida).removePlayer(session);
            if(!partidasOnline.get(partida).existenJugadores()){
                partidasOnline.remove(partida);
            }
        } 
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
    }

   
}