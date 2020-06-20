/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

/**
 *
 * @author J. Eduardo Arias
 */
public class Raqueta extends Superficie implements Movible{

    public Raqueta(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void move(int difx, int dify) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
