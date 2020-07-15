/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Elements;

import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author J. Eduardo Arias
 */
public abstract class Superficie {
    private int x; 
    private int y;
    private final int w;
    private final int h;  


    public Superficie(int x, int y, int w, int h) throws Exception {
        if (w <= 0 || h<=0){
            throw new Exception("Ancho/alto Invalido");
        }
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;        
    }    

    public synchronized Map<Dir, Rectangle> getColisiones(){
        Map<Dir,Rectangle> colisiones=new HashMap<Dir, Rectangle>();
        colisiones.put(Dir.UP, new Rectangle(x+1, y, w-1, 1));
        colisiones.put(Dir.DOWN, new Rectangle(x+1, y+h, w-1, 1));
        colisiones.put(Dir.LEFT, new Rectangle(x, y+1, 1, h-1));
        colisiones.put(Dir.RIGTH, new Rectangle(x+w, y+1, 1, h-1));
        return colisiones;
    }    
  
    public synchronized int getWidth(){
        return w;
    }
    public synchronized int getHeight(){
        return h;
    }
    public synchronized int getX(){
        return x;
    }
    public synchronized int getY(){
        return y;
    }
    public synchronized int getCenterX(){
        return x+w/2;
    }
    public synchronized int getCenterY(){
        return y+h/2;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }
    
    public synchronized Point ubicacion(){
        return new Point(x,y);
    }
    
       
    @Override
    public synchronized String toString(){        
        Point A=new Point(x, y);
        Point B=new Point(x+w,y);
        Point C=new Point(x+w,y+h);
        Point D=new Point(x,y+h);       
        return "Superficie = A:"+A+", B: "+B+" C:"+C+" D:"+D;
    }
    
}
