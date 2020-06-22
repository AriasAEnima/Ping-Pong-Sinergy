/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.ServiciosFisica.Dir;
import Elements.Mesa;
import Elements.Raqueta;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author J. Eduardo Arias
 */
public class JugadoresController {
    private final Map<String,Raqueta> jugadores;
    private final Mesa mesa;

    public JugadoresController(Mesa mesa) throws Exception {
        jugadores=new HashMap<String,Raqueta >();
        jugadores.put("jugador1", new Raqueta(150, 50, 50, 150));
        jugadores.put("jugador2", new Raqueta(600, 250, 50, 150));    
        this.mesa=mesa;
    }
    
    public synchronized void muevaJugador(String name,Dir dir) throws Exception{
        if(jugadores.get(name)==null){
            throw new Exception("Jugador no encontrado");
        }else{
            if(ServiciosFisica.puedeMoverJugador(mesa, jugadores.get(name), dir, jugadores.values())){
                jugadores.get(name).setDir(dir);
                jugadores.get(name).move();
            }        
        }
    }

    public synchronized List<Point> ubicacionJugadores() {
        List<Point> ans=new ArrayList<>();
        for (Raqueta ra: jugadores.values()){
            ans.add(ra.ubicacion());
        }
        return ans;
    }
    
    public synchronized Collection<Raqueta> getRaquetas(){
        return  jugadores.values();
    }

    public synchronized String getJugadorPorRaqueta(Raqueta r) {
        for(String key:jugadores.keySet()){
            if(jugadores.get(key)==r){
                return key;
            }
        }
        return null;
    }
    
    
}
