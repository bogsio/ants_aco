/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AntColonyOptimization_TSP;

/**
 *
 * @author bogdan
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bogdan
 */
public class SingleAnt{
    SingleAnt(int n){
        tour = new int[n+1];
        visited = new boolean[n];
        tourLength = 0;
        noNodes = n;
    }
    private int noNodes;
    private int tourLength;
    private int tour[];
    private boolean visited[];

    public int getTourLength(){
        return tourLength;
    }
    public void setTourLength(int len){
        tourLength = len;
    }
    public int getNoNodes(){
        return tourLength;
    }
    public void setNoNodes(int len){
        tourLength = len;
    }
    public boolean getVisited(int idx){
        return visited[idx];
    }
    public void setVisited(int idx, boolean val){
        visited[idx] = val;

    }
    public int getTour(int idx){
        return tour[idx];
    }
    public void setTour(int idx, int val){
        tour[idx] = val;

    }
    public int[] getTour(){
        return tour;
    }
}
