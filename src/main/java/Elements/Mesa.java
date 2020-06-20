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
public class Mesa extends Superficie{
   
    public Mesa(int x, int y, int w, int h) {
        super(x, y, w, h);
    }   
    
    public Mesa(int w, int h){
        this(0,0,w,h);
    }
    
    
}
