/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Elements;

import edu.escuelaing.arsw.Controllers.ServiciosFisica;

/**
 *
 * @author J. Eduardo Arias
 */
public class Raqueta extends Superficie implements Movible{
    
    private int dx;
    private int dy;
    private static final int MOVECONST=1;

    public Raqueta(int x, int y, int w, int h) throws Exception {
        super(x, y, w, h);
        dx=0;
        dy=0;
    }

    @Override
    public synchronized void move(int difx, int dify) {
        setX(getX()+difx);
        setY(getY()+dify);  
    }

    @Override
    public synchronized void setDir(ServiciosFisica.Dir dir) {
       switch (dir) {
            case LEFT:
                dx=-MOVECONST; 
                dy=0;
                break;
            case RIGTH:
                dx=MOVECONST;   
                dy=0;
                break;                 
            case UP:
                dy=-MOVECONST;      
                dx=0;
                break;
            case DOWN:
                dy=MOVECONST;     
                dx=0;
                break;
            case NONEHORIZONTAL:
                dx=0;               
                break;
            case NONEVERTICAL:
                dy=0;                
                break;             
        }
    }

    @Override
    public synchronized void move() {
         move(dx,dy);
    }
    
}
