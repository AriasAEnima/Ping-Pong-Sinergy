/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Controllers;

import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import edu.escuelaing.arsw.Elements.Mesa;
import edu.escuelaing.arsw.Elements.Raqueta;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author J. Eduardo Arias
 */
public class JugadoresController {
    private final Map<String,Raqueta> jugadores;
    private final Mesa mesa;
    private Observer ob;
    
    public JugadoresController (Mesa mesa, Observer ob) throws Exception{
        this(mesa);
        this.ob=ob;
    }
    

    public JugadoresController(Mesa mesa) throws Exception {
        jugadores=new HashMap< >();
//        jugadores.put("jugador1", new Raqueta(150, 50, 50, 150));
//        jugadores.put("jugador2", new Raqueta(600, 250, 50, 150));    
        this.mesa=mesa;
    }
    
    public JugadoresController(Mesa mesa,int njug) throws Exception {
        jugadores=new HashMap< >();
        for(int i=0; i<njug; i++){
             jugadores.put("jugador"+(i+1), new Raqueta("jugador"+(i+1),150+850*(i%2), 50 + 170*(i/2), 50, 150));
        }     
        
        
        this.mesa=mesa;
    }
    
    public synchronized void muevaJugador(String name,Dir dir) throws Exception{
        if(jugadores.get(name)==null){
            throw new Exception("Jugador no encontrado");
        }else{
            if(ServiciosFisica.puedeMoverJugador(mesa, jugadores.get(name), dir, jugadores.values())){
                jugadores.get(name).setDir(dir);
                jugadores.get(name).move();
                if(ob!=null){
                    ob.notifyChangue();
                }
            }        
        }
    }

    public synchronized List<Point> ubicacionJugadores() {
        List<Point> ans=new ArrayList<>();
        for (Raqueta ra: jugadores.values()){
            ans.add(ra.ubicacion());
        }
        System.out.println(JugadorestoJSONString());
        return ans;
    }
    
    public synchronized String JugadorestoJSONString() {
        boolean primero=true;
        String ans="\"jugadores\": [";
        for (Raqueta r:jugadores.values()){
            if(!primero){
                ans+=",";               
            }else{      
                primero=false;
            }
            ans+=r.toJSON().toString();          
        }      
        ans+="]";
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
