/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Controllers.ServiciosFisica.Dir;
import java.awt.Rectangle;

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
    
    public void move(){   
        move(dx,dy);
    }    
    
    @Override
    public void  move(int difx,int dify){
        setX(getX()+difx);
        setY(getY()+dify);             
    }    


    public void setDir(Dir dir) {      
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

    
}
