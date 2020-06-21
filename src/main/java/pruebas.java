
import Controllers.Arbitro;
import Controllers.ServiciosFisica;
import Controllers.ServiciosFisica.Dir;
import Elements.Mesa;
import Elements.Pelota;
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
            Arbitro a=new Arbitro(Dir.UP, Dir.LEFT);
            print("Jugadores");
            print(a.ubicacionJugadores());
            print("Pelota");
            print(a.ubicacionPelota());
//            for(int i=0 ; i<2000;i++){               
//                if (a.huboContactoPelota()){
//                    print(a.ubicacionPelota());
//                }
//                a.continuar();
//            }          
             
            for(int i=0 ; i<250-50;i++){   
                a.MoverJugador("jugador1", Dir.DOWN);
                print("Jugadores");
                print(a.ubicacionJugadores());   
            }
            for(int i=0 ; i<1000;i++){   
                a.MoverJugador("jugador1", Dir.RIGTH);
                print("Jugadores");
                print(a.ubicacionJugadores());   
            }
            
        } catch (Exception ex) {
            Logger.getLogger(pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }     
        
    }
    
    static void print(Object o){
        System.out.println(o);
    }
}
