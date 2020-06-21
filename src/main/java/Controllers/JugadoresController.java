/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.ServiciosFisica.Dir;
import Elements.Mesa;
import Elements.Raqueta;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author J. Eduardo Arias
 */
public class JugadoresController {
    private Map<String,Raqueta> jugadores;
    private Mesa mesa;

    public JugadoresController(Map<String, Raqueta> jugadores,Mesa mesa) {
        this.jugadores = jugadores;
        this.mesa=mesa;
    }
    
    public void muevaJugador(String name,Dir dir) throws Exception{
        if(jugadores.get(name)==null){
            throw new Exception("Jugador no encontrado");
        }else{
            if(ServiciosFisica.puedeMoverJugador(mesa, jugadores.get(name), dir, jugadores.values())){
                jugadores.get(name).setDir(dir);
                jugadores.get(name).move();
            }        
        }
    }

    public List<String> ubicacionJugadores() {
        List<String> ans=new ArrayList<>();
        for (Raqueta ra: jugadores.values()){
            ans.add(ra.toString());
        }
        return ans;
    }
    
    
    
}
