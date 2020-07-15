/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import edu.escuelaing.arsw.Controllers.Arbitro;
import edu.escuelaing.arsw.Controllers.JugadoresController;
import edu.escuelaing.arsw.Controllers.ServiciosFisica;
import edu.escuelaing.arsw.Elements.Mesa;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author J. Eduardo Arias
 */
public class BasicCollisionTest {
    
    public BasicCollisionTest() {
    }
    
    @Test
    public void PelotaDeberiaRebotarEnMesa(){        
        // Mesa es de 700 x 500
        // La pelota es de 50 x 50 inicia en el centro.
      try {   
            Arbitro a=new Arbitro();
            a.PreparePartida(ServiciosFisica.Dir.UP, ServiciosFisica.Dir.LEFT,0); // 0 jugadores Va hacia arriba a la izquierda
            a.continuar(); // Mueve la pelota             
            List<Point> dondeReboto = new ArrayList<>();
            for(int i=0 ; i<500;i++){                  
                    if(a.huboContactoPelota()){
                        dondeReboto.add(a.ubicacionPelota());
                    }
                    a.continuar();                  
            }          
            
            // Deberia rebotar en la pared superior y en la izquierda
            Point primero=dondeReboto.get(0);
              Point segundo=dondeReboto.get(1);
            assertTrue(primero.x==100 && primero.y==0 && segundo.x==0 && segundo.y==100);                 
            
        } catch (Exception ex) {
            Logger.getLogger(BasicCollisionTest.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    @Test
    public void PelotaDeberiaRebotarEnMesa2(){        
        // Mesa es de 700 x 500
        // La pelota es de 50 x 50 inicia en el centro.
      try {   
            Arbitro a=new Arbitro();
            a.PreparePartida(ServiciosFisica.Dir.DOWN, ServiciosFisica.Dir.RIGTH,0); // 0 jugadores Va hacia abajo a la derecha
            a.continuar(); // Mueve la pelota             
            List<Point> dondeReboto = new ArrayList<>();
            for(int i=0 ; i<500;i++){                  
                    if(a.huboContactoPelota()){
                        dondeReboto.add(a.ubicacionPelota());
                    }
                    a.continuar();                  
            }          
            
            // Deberia rebotar en la pared superior y en la izquierda
            Point primero=dondeReboto.get(0);
              Point segundo=dondeReboto.get(1);            
            assertTrue(primero.x==700-100-50 && primero.y==500-50 && segundo.x==700-50 && segundo.y==500-100-50);                 
            
        } catch (Exception ex) {
            Logger.getLogger(BasicCollisionTest.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
        @Test
    public void JugadorNoSaleDeMesa(){        
        // Mesa es de 700 x 500
        // La pelota es de 50 x 50 inicia en el centro.
      try {   
            Mesa mesa=new Mesa(700,500);
             JugadoresController jc=new JugadoresController(mesa, 1); // Iniciamente queda en la posicion 150 , 50
         
           
            for(int i=0 ; i<500;i++){       
                 jc.muevaJugador("jugador1", ServiciosFisica.Dir.UP);
            }
            Point jugador1=jc.ubicacionJugadores().get(0);
            assertTrue(jugador1.x==150 && jugador1.y==0);
            
             for(int i=0 ; i<500;i++){       
                 jc.muevaJugador("jugador1", ServiciosFisica.Dir.RIGTH);
            }
             jugador1=jc.ubicacionJugadores().get(0);
            assertTrue(jugador1.x==700-50 && jugador1.y==0);
                  
       
            
        } catch (Exception ex) {
            Logger.getLogger(BasicCollisionTest.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    @Test 
    public void PelotaChocaConJugador(){
        try {
            Arbitro a=new Arbitro();                
            a.PreparePartida(ServiciosFisica.Dir.UP, ServiciosFisica.Dir.LEFT);
              // jugadores.put("jugador1", new Raqueta(150, 50, 50, 150));     jugadores.put("jugador2", new Raqueta(600, 250, 50, 150));    
  
           List<Point> dondeReboto = new ArrayList<>();
            for(int i=0 ; i<500;i++){                  
                    if(a.huboContactoPelota()){
                        dondeReboto.add(a.ubicacionPelota());
                    }         
                    a.continuar();      
            }      
            System.out.println(dondeReboto);
            Point primero = dondeReboto.get(0);
            Point segundo = dondeReboto.get(1);
            Point tercero = dondeReboto.get(2);
            assertTrue(primero.x==200 && primero.y==100);
            assertTrue(segundo.x==300 && segundo.y==0);
            assertTrue(tercero.x==550 && tercero.y==250);
            
     
        } catch (Exception ex) {
            Logger.getLogger(BasicCollisionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
