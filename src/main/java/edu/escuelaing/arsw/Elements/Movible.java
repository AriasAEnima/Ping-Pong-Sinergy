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
public interface Movible{

   public void move(int difx,int dify);    
   
   public void move();
   
   public void setDir(ServiciosFisica.Dir dir);
}
