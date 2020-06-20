
import Controllers.Arbitro;
import Elements.Ball.Dir;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author J. Eduardo Arias
 */
public class pruebas {
    public static void main(String[] args){      
        Arbitro ar=new Arbitro(Dir.DOWN,Dir.RIGTH);
        print(ar.getMesa());
        print(ar.getPelota());
        Dir newdir=ar.getPelota().ColisionMesa(ar.getMesa());    
        while(newdir==null){
            ar.moveBall();
            newdir=ar.getPelota().ColisionMesa(ar.getMesa());
        }
        print("Resultado");
        print(ar.getMesa());
        print(ar.getPelota());
        print(newdir);       
        while(newdir!=null){
            ar.getPelota().setDir(newdir);
            ar.moveBall();         
            newdir=ar.getPelota().ColisionMesa(ar.getMesa());        
        }
       
        while(newdir==null){
            ar.moveBall();
            newdir=ar.getPelota().ColisionMesa(ar.getMesa());
        }
        print("Resultado 2");
        print(ar.getMesa());
        print(ar.getPelota());
        
    }
    
    static void print(Object o){
        System.out.println(o);
    }
}
