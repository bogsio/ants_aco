/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicalSolverTSP;

/**
 *
 * @author bogdan
 */

public class PointCoords {
    private int x;
    private int y;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getIntegerDistance(PointCoords p){
        return (int)Math.round(Math.sqrt(Math.pow(this.x-p.x, 2) + Math.pow(this.y-p.y,2)));
    }
    PointCoords(){
        x = y = 0;
    }
    PointCoords(int x,int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "[ "+x+", "+y+" ]";
    }


}
