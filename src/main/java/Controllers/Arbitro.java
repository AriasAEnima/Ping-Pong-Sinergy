/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Elements.Ball;
import Elements.Ball.Dir;
import Elements.Mesa;


/**
 *
 * @author J. Eduardo Arias
 */
public class Arbitro {
    private Ball pelota;
    private Mesa mesa;
    
    public Arbitro(Dir h,Dir v) {
       mesa=new Mesa(700,500);
       pelota=new Ball(100, 100, mesa);
       pelota.setDir(h);
       pelota.setDir(v);     
    }
    
    
    public void moveBall(){
        pelota.move();
    }

    public Ball getPelota() {
        return pelota;
    }

    public Mesa getMesa() {
        return mesa;
    }
    
    

    
  
    
}
