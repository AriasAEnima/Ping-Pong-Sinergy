/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arsw.Elements;

import java.awt.Rectangle;

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

    
    
}