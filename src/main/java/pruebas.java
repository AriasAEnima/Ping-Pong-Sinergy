
import edu.escuelaing.arsw.Controllers.Arbitro;
import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author J. Eduardo Arias
 */
public class pruebas {
    public static void main(String[] args){      
        try {   
            Arbitro a=new Arbitro();
            a.PreparePartida(Dir.UP, Dir.LEFT);
            print("Jugadores");
            print(a.ubicacionJugadores());
            print("Pelota");
            print(a.ubicacionPelota());
            Thread ta=new Thread(a);
            ta.start();                    
             
            for(int i=0 ; i<800;i++){   
                a.MoverJugador("jugador1", Dir.RIGTH);
                Thread.sleep(10);
                print("Jugadores");              
                print(a.ubicacionJugadores());   
            }          
            System.out.println("Vo a parar");
            a.stop();
            
        } catch (Exception ex) {
            Logger.getLogger(pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }     
        
    }
    
    static void print(Object o){
        System.out.println(o);
    }
}
