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
import edu.escuelaing.arsw.Controllers.ServiciosFisica;
import java.awt.Point;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@ServerEndpoint("/partidaService")
public class PartidaEndPoint {


    private static final Logger logger = Logger.getLogger(PartidaEndPoint.class.getName());
    /* Queue for all open WebSocket sessions */
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    Session ownSession = null;
    Arbitro ar=null;

    /* Call this method to send a message to all clients */
    public void send(String msg) {
        try {
            /* Send updates to all open WebSocket sessions */
            for (Session session : queue) {
                if (!session.equals(this.ownSession)) {
                    session.getBasicRemote().sendText(msg);
                }
                logger.log(Level.INFO, "Sent: {0}", msg);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnMessage
    public void processMove(String message, Session session) {
        logger.log(Level.INFO, "Move received:" + message + ". From session: " + session);
        this.send(message);
    }

    @OnOpen
    public void openConnection(Session session) {
        /* Register this connection in the queue */
        queue.add(session);
        ownSession = session;
        logger.log(Level.INFO, "Connection opened.");
        try {
        
            ar=new Arbitro();
            ar.PreparePartida(ServiciosFisica.Dir.UP, ServiciosFisica.Dir.RIGTH);
            ownSession.getBasicRemote().sendText("Connection established.");
            Point pelotau=ar.ubicacionPelota();
            ownSession.getBasicRemote().sendText("{ \"pelota\" : [ {\"x\": "+ pelotau.x+", \"y\":"+pelotau.y+"}]}");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PartidaEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }


    @OnClose
    public void closedConnection(Session session) {
        /* Remove this connection from the queue */
        queue.remove(session);
        logger.log(Level.INFO, "Connection closed for session " + session);
    }


    @OnError
    public void error(Session session, Throwable t) {
        /* Remove this connection from the queue */
        queue.remove(session);
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
    }
}