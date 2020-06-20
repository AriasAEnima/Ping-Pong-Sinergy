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
public class Ball extends Superficie implements Movible{
   
    public enum Dir {
        LEFT,
        RIGTH, 
        UP,
        DOWN,
        NONEHORIZONTAL,
        NONEVERTICAL
    }         
    
    private int dx;
    private int dy;
    private static final int MOVECONST=1;
    private Dir vertical;
    private Dir horizontal;
    
   
    public Ball(int x, int y, int w, int h) {
        super(x, y, w, h);
        dx=0;
        dy=0;
    }
    
    public Ball(int w,int h,Superficie s){
        this(s.getCenterX()-w/2,s.getCenterY()-h/2,w,h);
    }
    
    public void move(){   
        move(dx,dy);
    }    
    
    @Override
    public void  move(int difx,int dify){
        setX(getX()+difx);
        setY(getY()+dify);
        setXright(getXright()+difx);
        setYdown(getYdown()+dify);       
    }

    public void setDir(Dir dir) {      
        switch (dir) {
            case LEFT:
                dx=-MOVECONST;
                horizontal=dir;
                break;
            case RIGTH:
                dx=MOVECONST;
                horizontal=dir;
                break;                 
            case UP:
                dy=-MOVECONST;
                vertical=dir;
                break;
            case DOWN:
                dy=MOVECONST;
                vertical=dir;
                break;
            case NONEHORIZONTAL:
                dx=0;
                vertical=dir;
                break;
            case NONEVERTICAL:
                dy=0;
                vertical=dir;
                break;             
        }
    }
  
    
    public Dir ColisionMovible(Raqueta player){              
       if( player.getX()<= this.getXright() &&
                player.getXright() >= this.getX() &&
                player.getY() <= this.getYdown() &&
                player.getYdown() >= this.getY()){
           if (Math.abs(player.getX()-this.getX())<Math.abs(player.getX()-this.getY())){
               return this.vertical==Dir.UP?Dir.DOWN:Dir.UP;
           }else{
               return this.horizontal==Dir.LEFT?Dir.RIGTH:Dir.LEFT;
           }
       }else{
              return null;        
       }
    
    }
    
    public Dir ColisionMesa(Mesa mesa){
       if (this.getX()<=mesa.getX()){
           return Dir.RIGTH;
       }else if(this.getXright()>=mesa.getXright()){
           return Dir.LEFT;
       }else if(this.getY()<= mesa.getY()){
           return Dir.DOWN;
       }else if(this.getYdown()>=mesa.getYdown()){
           return Dir.UP;
       }else{
           return null;
       }
    } 
    
}
