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
import edu.escuelaing.arsw.Controllers.Arbitro;
import edu.escuelaing.arsw.Controllers.Observer;
import edu.escuelaing.arsw.Controllers.ServiciosFisica;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class PartidaEndPoint implements Observer{


    private static final Logger logger = Logger.getLogger(PartidaEndPoint.class.getName());
    /* Queue for all open WebSocket sessions */ 
    public static ConcurrentHashMap<String,Session[]> salas= new ConcurrentHashMap<String,Session[]>();
    public static ConcurrentHashMap<String,Arbitro> partidas= new ConcurrentHashMap<String,Arbitro>();
    public static ConcurrentHashMap<String,List<Session>> espectadores= new ConcurrentHashMap<String,List<Session>>();
    
    private Session ownSession = null;
    private Arbitro ar=null;    
    private boolean enviar=true;
    private String partida="";
    private boolean espectador=false;
    
    
    /* Call this method to send a message to all clients */
    public void send(String msg) {
        try {
            /* Send updates to all open WebSocket sessions */
            Session[] ses=salas.get(partida);
            for (int i=0; i<ses.length ; i++){
                if(ses[i]!=null){
                    synchronized(partidas.get(partida)){
                          ses[i].getBasicRemote().sendText(msg);  
                    }                    
                }
            }
            if (espectadores.get(partida) != null) {
                for (Session e : espectadores.get(partida)) {
                    synchronized (partidas.get(partida)) {
                        e.getBasicRemote().sendText(msg);
                    }
                }
            }

        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnMessage
    public synchronized void processMove(String message, Session session) {
        //logger.log(Level.INFO, "Move received:" + message + ". From session: " + session);
        JSONObject json = new JSONObject(message);
        if(json.has("sala")){
           int sala=json.getInt("sala");
           partida=sala+"";
           iniciarPartida(ownSession);
        }
        if(json.has("jugador") && !espectador){
           String jugador=json.getString("jugador");
           String dir=json.getString("DIR");
           ServiciosFisica.Dir D= ServiciosFisica.Dir.valueOf(dir);         
           ar.MoverJugador(jugador, D);
           this.send(ar.ElementosToJson());
        }      
        
    }
    
    private void iniciarPartida(Session session){
        try {        
            if(partidas.get(partida)==null){
                ar=new Arbitro(this);
                ar.PreparePartida(ServiciosFisica.Dir.UP, ServiciosFisica.Dir.RIGTH,2);
                partidas.put(partida, ar);
                salas.put(partida, new Session[]{ownSession,null});
                ownSession.getBasicRemote().sendText("Connection established.");  
                ownSession.getBasicRemote().sendText("{\"you\":\"jugador1\"}"); 
                ownSession.getBasicRemote().sendText(ar.ElementosToJson());                       
            }else if (salas.get(partida)[1]==null || salas.get(partida)[0]==null) {
                ar=partidas.get(partida);       
                if(salas.get(partida)[1]==null){
                     salas.get(partida)[1]=ownSession;
                }else{
                     salas.get(partida)[0]=ownSession;
                }               
                ownSession.getBasicRemote().sendText("Connection established.");  
                ownSession.getBasicRemote().sendText("{\"you\":\"jugador2\"}"); 
                ownSession.getBasicRemote().sendText(ar.ElementosToJson());   
                new Thread(ar).start();            
            }else{
                espectador=true;
                if(espectadores.get(partida)==null){
                   List<Session> esp=new ArrayList<>();
                   esp.add(session);
                   espectadores.put(partida,esp);
                }else{
                    espectadores.get(partida).add(session);
                }
                ownSession.getBasicRemote().sendText("Connection established.");  
                ownSession.getBasicRemote().sendText("{\"you\":\"espectador\"}"); 
                ownSession.getBasicRemote().sendText(ar.ElementosToJson());   
                
            }        
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PartidaEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(salas);
    }

    @OnOpen
    public void openConnection(Session session) {
        /* Register this connection in the queue */   
        ownSession = session;
        logger.log(Level.INFO, "Connection opened.");

    }


    @OnClose
    public void closedConnection(Session session) {
        enviar=false;      
        /* Remove this connection from the queue */
    
        if (salas.get(partida) != null && !espectador) {
            if (salas.get(partida)[0] == ownSession) {
                salas.get(partida)[0] = null;
            } else {
                salas.get(partida)[1] = null;
            }

            if (salas.get(partida)[0] == null && salas.get(partida)[0] == null) {
                salas.remove(partida);
                ar.pare();
                partidas.remove(partida);
            }
        }else if(espectador && espectadores.get(partida)!=null){
            espectadores.get(partida).remove(ownSession);
            if(espectadores.get(partida).isEmpty()){
                espectadores.remove(partida);
            }
        }
        logger.log(Level.INFO, "Connection closed for session " + session);       
    }


    @OnError
    public void error(Session session, Throwable t) {
        if (salas.get(partida) != null && !espectador) {
            if (salas.get(partida)[0] == ownSession) {
                salas.get(partida)[0] = null;
            } else {
                salas.get(partida)[1] = null;
            }

            if (salas.get(partida)[0] == null && salas.get(partida)[0] == null) {
                salas.remove(partida);
                ar.pare();
                partidas.remove(partida);
            }
        }else if(espectador && espectadores.get(partida)!=null){
            espectadores.get(partida).remove(ownSession);
            if(espectadores.get(partida).isEmpty()){
                espectadores.remove(partida);
            }
        }
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
    }

    @Override
    public synchronized void notifyChangue() { 
        if(enviar){
           send(ar.ElementosToJson());
        }        
        if(ar.termino()){
            send("{\"termino\": true }");
        }
    }
}