/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import java.awt.Rectangle;

/**
 *
 * @author J. Eduardo Arias
 */
public abstract class Superficie {
    private int x; 
    private int y;
    private int xright;
    private int ydown;
    private final int w;
    private final int h;


    public Superficie(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        xright=x+w;
        ydown=y+h;
    }    

    public int getXright() {
        return xright;
    }

    public int getYdown() {
        return ydown;
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

    public void setXright(int xright) {
        this.xright = xright;
    }

    public void setYdown(int ydown) {
        this.ydown = ydown;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
       
    @Override
    public String toString(){
        
        return "Superficie:\nX: ["+this.getX()+","+this.getXright()+"]\nY: ["+this.getY()+","+this.getYdown()+"]";
    }
    
}
