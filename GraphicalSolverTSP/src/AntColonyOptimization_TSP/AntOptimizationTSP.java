/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AntColonyOptimization_TSP;

/**
 *
 * @author bogdan
 */
public interface AntOptimizationTSP {
    public double getPheromone(int i, int j);
    public double getHeuristic(int i, int j);
    public int getDistance(int i, int j);
    public void setPheromone(int i, int j, double ph);
    public void setHeuristic(int i, int j, double h);
    public void setDistance(int i, int j, int d);
    public void setAlfa(double a);
    public void setBeta(double b);
    public void setRo(double r);
    public double getAlfa();
    public double getBeta();
    public double getRo();
    public int[] getBestTour();
    public void setIteration(int iter);
    public int getIteration();
    public int getNoAnts();
    public void setNoAnts(int ants);
    public int getNoNodes();
    public void setNoNodes(int nodes);
    public void initData();
    public void initPheromones();
    public void computeHeuristic();
    public void initAnts();
    public void constructSolutions();
    public void globalEvaporation();
    public void updatePheromones();
    public void depositPheromone(int idx);
    public void localSearch();
    public int[] getBestSoFarTour();
    public void updateBestSoFarTour();
    public double[][] getPheromoneMatrix();
}
