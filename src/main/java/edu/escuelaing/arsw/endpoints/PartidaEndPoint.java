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
import java.util.logging.Level;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    Session ownSession = null;
    Arbitro ar=null;
    Thread h=null;
    boolean enviar=true;
    /* Call this method to send a message to all clients */
    public synchronized void send(String msg) {
        try {
            /* Send updates to all open WebSocket sessions */
            for (Session session : queue) {               
                session.getBasicRemote().sendText(msg);           
                //logger.log(Level.INFO, "Sent: {0}", msg);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnMessage
    public void processMove(String message, Session session) {
        //logger.log(Level.INFO, "Move received:" + message + ". From session: " + session);
        JSONObject json = new JSONObject(message);
        if(json.has("jugador")){
           String jugador=json.getString("jugador");
           String dir=json.getString("DIR");
           ServiciosFisica.Dir D= ServiciosFisica.Dir.valueOf(dir);
           System.out.println(D);
           ar.MoverJugador(jugador, D);
        }
        this.send(ar.ElementosToJson());
    }

    @OnOpen
    public void openConnection(Session session) {
        /* Register this connection in the queue */
        queue.add(session);
        ownSession = session;
        logger.log(Level.INFO, "Connection opened.");
        try {        
            ar=new Arbitro(this);
            ar.PreparePartida(ServiciosFisica.Dir.UP, ServiciosFisica.Dir.RIGTH,2);
            ownSession.getBasicRemote().sendText("Connection established.");          
            ownSession.getBasicRemote().sendText(ar.ElementosToJson());
            h=new Thread(ar);
            h.start();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PartidaEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }


    @OnClose
    public void closedConnection(Session session) {
        enviar=false;
        try {
            /* Remove this connection from the queue */
            ar.pare();
            h.join();
            queue.remove(session);
            logger.log(Level.INFO, "Connection closed for session " + session);
        } catch (InterruptedException ex) {
            Logger.getLogger(PartidaEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @OnError
    public void error(Session session, Throwable t) {
        /* Remove this connection from the queue */
        queue.remove(session);
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
    }

    @Override
    public void notifyChangue() { 
        if(enviar){
           send(ar.ElementosToJson());
        }        
    }
}