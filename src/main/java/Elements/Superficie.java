/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Controllers.ServiciosFisica.Dir;
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

    public Map<Dir, Rectangle> getColisiones(){
        Map<Dir,Rectangle> colisiones=new HashMap<Dir, Rectangle>();
        colisiones.put(Dir.UP, new Rectangle(x+1, y, w-1, 1));
        colisiones.put(Dir.DOWN, new Rectangle(x+1, y+h, w-1, 1));
        colisiones.put(Dir.LEFT, new Rectangle(x, y+1, 1, h-1));
        colisiones.put(Dir.RIGTH, new Rectangle(x+w, y+1, 1, h-1));
        return colisiones;
    }    
  
    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getCenterX(){
        return x+w/2;
    }
    public int getCenterY(){
        return y+h/2;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
       
    @Override
    public String toString(){        
        Point A=new Point(x, y);
        Point B=new Point(x+w,y);
        Point C=new Point(x+w,y+h);
        Point D=new Point(x,y+h);       
        return "Superficie = A:"+A+", B: "+B+" C:"+C+" D:"+D;
    }
    
}
