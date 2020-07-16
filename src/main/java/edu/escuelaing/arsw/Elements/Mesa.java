/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Elements;

import edu.escuelaing.arsw.Controllers.ServiciosFisica;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author J. Eduardo Arias
 */
public class Mesa extends Superficie{
   
   
    public Mesa(int x, int y, int w, int h) throws Exception {
        super(x, y, w, h);
    }   
    
    public Mesa(int w, int h) throws Exception{
        this(0,0,w,h);
    }    

    
        
    public synchronized JSONObject toJSON() {
        JSONObject jo = new JSONObject();       
        jo.put("x", this.getX());
        jo.put("y", this.getY());
        return jo;
    }
    
//    @Override
//    public synchronized Map<ServiciosFisica.Dir, Rectangle> getColisiones(){
//        Map<ServiciosFisica.Dir,Rectangle> colisiones=new HashMap<ServiciosFisica.Dir, Rectangle>();
//        colisiones.put(ServiciosFisica.Dir.UP, new Rectangle(getX()+1, getY(), getWidth()-1, 10));
//        colisiones.put(ServiciosFisica.Dir.DOWN, new Rectangle(getX()+1, getY()+getHeight(), getWidth()-1, 10));
//        colisiones.put(ServiciosFisica.Dir.LEFT, new Rectangle(getX(), getY()+1, 10, getWidth()-1));
//        colisiones.put(ServiciosFisica.Dir.RIGTH, new Rectangle(getX()+getWidth(), getY()+1, 10, getWidth()-1));
//        return colisiones;
//    } 

}
