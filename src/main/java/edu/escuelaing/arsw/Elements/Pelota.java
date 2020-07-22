/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Elements;

import edu.escuelaing.arsw.Controllers.ServiciosFisica.Dir;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author J. Eduardo Arias
 */
public class Pelota extends Superficie implements Movible{
    
    
    private int dx;
    private int dy;
    private static final int MOVECONST=1;
    
   
    public Pelota(int x, int y, int w, int h) throws Exception {   
        super(x, y, w, h);
        dx=0;
        dy=0;
    }
    
    public Pelota(int w,int h,Superficie s) throws Exception{
        this(s.getCenterX()-w/2,s.getCenterY()-h/2,w,h);
    }
    
    public synchronized void move(){   
        move(dx,dy);
    }    
    
    @Override
    public synchronized void  move(int difx,int dify){
        setX(getX()+difx);
        setY(getY()+dify);             
    }    


    public synchronized void setDir(Dir dir) {      
        switch (dir) {
            case LEFT:
                dx=-MOVECONST;             
                break;
            case RIGTH:
                dx=MOVECONST;             
                break;                 
            case UP:
                dy=-MOVECONST;               
                break;
            case DOWN:
                dy=MOVECONST;              
                break;
            case NONEHORIZONTAL:
                dx=0;               
                break;
            case NONEVERTICAL:
                dy=0;                
                break;             
        }
    }  
    
    public synchronized JSONObject toJSON() {
        JSONObject jo = new JSONObject();       
        jo.put("x", this.getX());
        jo.put("y", this.getY());
        return jo;
    }

    public void setRelative(Mesa mesa) {
      setX(mesa.getCenterX()-getWidth()/2);
      setY(mesa.getCenterY()-getHeight()/2);
    }
    
    @Override
    public synchronized Map<Dir, Rectangle> getColisiones(){
         Map<Dir,Rectangle> colisiones=new HashMap<Dir, Rectangle>();
        colisiones.put(Dir.UP, new Rectangle(getX()+3, getY(), getWidth()-6, 10));
        colisiones.put(Dir.DOWN, new Rectangle(getX()+3,getY()+getHeight()-10, getWidth()-6, 10));
        colisiones.put(Dir.LEFT, new Rectangle(getX(), getY()+3, 10, getHeight()-6));
        colisiones.put(Dir.RIGTH, new Rectangle(getX()+getWidth()-10, getY()+3, 10, getHeight()-6));
        return colisiones;
    }    

    
}
