/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AntColonyOptimization_TSP;

/**
 *
 * @author bogdan
 */
import java.util.Random;

public class AntSystemTSP implements AntOptimizationTSP{
    /* matricea de distante */
    private int dist[][];
    /* matricea de feromoni */
    private double pheromone[][];
    /* matricea valorilor euristice */
    private double choiceInfo[][];
    /* vectorul de furnici artificiale */
    private SingleAnt ants[];

    /* numarul de furnici */
    private int noAnts;
    /* numarul de noduri */
    private int noNodes;
    /* parametrul alfa */
    private double alfa;
    /* parametrul beta */
    private double beta;
    /* parametrul ro */
    private double ro;
    /* iteratia curenta */
    private int iterations;
    /* daca se aplica localsearch */
    private boolean doLocalSearch;
    /* daca se aplica opt2 */
    private boolean doOpt2;
    /* daca se aplica opt3 */
    private boolean doOpt3;
    /* cel mai bun tur gasit pana acum */
    private int[] bestSoFarTour;
    private OptimizationTSP opt;

    public AntSystemTSP(int noNodes, int noAnts){
        this.noNodes = noNodes;
        this.noAnts = noAnts;
        this.alfa = 1.0;
        this.beta = 3.0;
        this.ro = 0.5;
        this.iterations = 0;

        dist = new int[noNodes][noNodes];
        pheromone = new double[noNodes][noNodes];
        choiceInfo = new double[noNodes][noNodes];

        ants = new SingleAnt[noAnts];
        for(int i = 0; i < noAnts; i++){
            ants[i] = new SingleAnt(noNodes);
        }
        //this.doLocalSearch = false;
        this.doOpt2 = false;
        this.doOpt3 = false;
        bestSoFarTour = new int[noNodes+1];
    }
    public AntSystemTSP(int noNodes, int noAnts, double alfa,
                    double beta, double ro, boolean doOpt2, boolean doOpt3){
        this.noNodes = noNodes;
        this.noAnts = noAnts;
        this.alfa = alfa;
        this.beta = beta;
        this.ro = ro;
        this.iterations = 0;
        
        dist = new int[noNodes][noNodes];
        pheromone = new double[noNodes][noNodes];
        choiceInfo = new double[noNodes][noNodes];

        ants = new SingleAnt[noAnts];
        for(int i = 0; i < noAnts; i++){
            ants[i] = new SingleAnt(noNodes);
        }
        //this.doLocalSearch = doLocalSearch;
        this.doOpt2 = doOpt2;
        this.doOpt3 = doOpt3;
        bestSoFarTour = new int[noNodes+1];
    }

    public SingleAnt getAnt(int k){
        return ants[k];
    }

    public double getPheromone(int i, int j){
        return pheromone[i][j];
    }
    public double getHeuristic(int i, int j){
        return choiceInfo[i][j];
    }
    public int getDistance(int i, int j){
        return dist[i][j];
    }
    public void setPheromone(int i, int j, double ph){
        pheromone[i][j] = ph;
    }
    public void setHeuristic(int i, int j, double h){
        choiceInfo[i][j] = h;
    }
    public void setDistance(int i, int j, int d){
        dist[i][j] = d;
    }
    public void setAlfa(double a){
        alfa = a;

    }
    public void setBeta(double b){
        beta = b;
    }
    public void setRo(double r){
        ro = r;
    }
    public double getAlfa(){
        return alfa;
    }
    public double getBeta(){
        return beta;
    }
    public double getRo(){
        return ro;
    }
    public int[] getBestTour(){
        int[] bestTour = new int[noNodes+1];
        int bestTourLength = Integer.MAX_VALUE;
        for(int i = 0; i < noAnts; i++){
            if(ants[i].getTourLength() < bestTourLength){
                bestTourLength = ants[i].getTourLength();
                for(int j = 0; j <=noNodes; j++)
                    bestTour[j] = ants[i].getTour(j);
            }
        }
        return bestTour;
    }
    public int getBestTourLength(){
        return computeTourLength(getBestTour());
    }
    public int[] getBestSoFarTour(){
        return bestSoFarTour;
    }
    public void updateBestSoFarTour(){
        if(getBestTourLength() < computeTourLength(bestSoFarTour)){
            bestSoFarTour = getBestTour();
        }
    }
    public void setIteration(int iter){
        iterations = iter;
    }
    public int getIteration(){
        return iterations;
    }
    public int getNoAnts(){
        return noAnts;
    }
    public void setNoAnts(int ants){
        noAnts = ants;
    }
    public int getNoNodes(){
        return noNodes;
    }
    public void setNoNodes(int nodes){
        noNodes = nodes;
    }
    public void initData(){
        int i,j;
        for(i = 0; i < noNodes; i++)
            for(j = 0; j < noNodes; j++){
                dist[i][j] = 0;
                pheromone[i][j] = 0.0;
                choiceInfo[i][j] = 0.0;
            }
    }
    public void initPheromones(){
        int i,j;
        double tau0 = computePheromone0();

        for(i = 0; i < noNodes; i++)
            for(j = 0; j < noNodes; j++)
                pheromone[i][j] = tau0;

        for(i = 0; i < noNodes; i++)
            pheromone[i][i] = 0;
        
        opt = new OptimizationTSP(dist);
    }
    public void computeHeuristic(){
        double niu;
        int i,j;

        for(i = 0; i < noNodes; i++)
            for(j = 0; j < noNodes; j++){
                if(dist[i][j] > 0)
                    niu = 1.0/dist[i][j];
                else
                    niu = 1.0/0.0001;
            choiceInfo[i][j] = Math.pow(pheromone[i][j],alfa)*Math.pow(niu,beta);
        }
    }
    public void initAnts(){
        int i,j;
        for(i = 0; i < noAnts; i++){
            ants[i].setTourLength(0);

            for(j = 0; j < noNodes; j++)
                ants[i].setVisited(j, false);
            for(j = 0; j <= noNodes; j++)
                ants[i].setTour(j, 0);
        }
    }
    public void decisionRule(int k, int step){
        /* k = identificator furnica */
        /* step = pasul curent din constructia solutiei */

        int c = ants[k].getTour(step-1); // orasul anterior al furnicii curente
        double sumProb = 0.0;

        double selectionProbability[] = new double[noNodes];

        int j;
        for(j = 0; j < noNodes; j++){
            if((ants[k].getVisited(j)) || (j == c))
                selectionProbability[j] = 0.0;
            else{
                selectionProbability[j] = choiceInfo[c][j];
                sumProb+=selectionProbability[j];
            }

        }
        double prob = Math.random()*sumProb;
        j = 0;
        double p = selectionProbability[j];
        while(p < prob){
            j++;
            p += selectionProbability[j];
        }
        ants[k].setTour(step, j);
        ants[k].setVisited(j, true);

    }
    public void constructSolutions(){
        /* stergere memorie furnici */
        initAnts();

        int step = 0;
        int k;
        int r;

        Random rand = new Random();

        /* asignare oras initial */
        for(k = 0; k < noAnts; k++){
            r = Math.abs(rand.nextInt())%noNodes;

            ants[k].setTour(step,r);
            ants[k].setVisited(r,true);
        }
        /* construirea efectiva a solutiei */
        while(step < noNodes-1){
            step++;
            for(k = 0; k < noAnts; k++)
                decisionRule(k,step);
        }
        /* completarea turului */
        for(k = 0; k < noAnts; k++){
            ants[k].setTour(noNodes,ants[k].getTour(0));
            ants[k].setTourLength(computeTourLength(ants[k].getTour()));
        }
        updateBestSoFarTour();
    }
    public void globalEvaporation(){
        int i,j;
        for(i = 0; i < noNodes; i++)
            for(j = 0; j < noNodes; j++){
                pheromone[i][j] = (1-ro)*pheromone[i][j];
            }
    }
    public void depositPheromone(int k){
        double delta_tau = 1.0/ants[k].getTourLength();
        int left,right;
        for(int i = 0; i < noNodes; i++){
            left = ants[k].getTour(i);
            right = ants[k].getTour(i+1);
            pheromone[left][right]+=delta_tau;
            pheromone[right][left]+=delta_tau;
        }
    }
    public void updatePheromones(){
        globalEvaporation();
        for(int k = 0; k < noAnts; k++)
            depositPheromone(k);
        computeHeuristic();
    }
    private int greedyTour(){
        boolean visited[] = new boolean[noNodes];
        int tour[] = new int[noNodes+1];
        int length;
        int min, node;
        int i,j;

        for(i = 0; i < noNodes; i++)
            visited[i] = false;

        tour[0] = 0;
        bestSoFarTour[0] = 0;
        visited[0] = true;

        for(i = 1; i < noNodes; i++){
            min = Integer.MAX_VALUE;
            node = -1;
            for(j = 0; j < noNodes; j++){
                if((!visited[j])&&(j!=tour[i-1])){
                    if(min > dist[tour[i-1]][j]){
                        min = dist[tour[i-1]][j];
                        node = j;
                    }
                }
            }
            tour[i] = node;
            bestSoFarTour[i] = node;
            visited[node] = true;
        }
        tour[noNodes] = tour[0];
        bestSoFarTour[noNodes] = bestSoFarTour[0];
        return computeTourLength(tour);

    }
    public int computeTourLength(int tour[]){
        int len = 0;
        for(int i = 0; i < noNodes; i++){
            len+=dist[tour[i]][tour[i+1]];
        }
        return len;
    }
    private double computePheromone0(){
        return ((double)noAnts)/((double)greedyTour());
    }
    
    public void localSearch(){
    /* Procedurile de cautare locala */
        if(doOpt2){
            for(int i = 0; i < noAnts; i++){
                opt.opt2(ants[i].getTour());
            }
        }
        if(doOpt3){
            for(int i = 0; i < noAnts; i++){
                opt.opt3(ants[i].getTour());
            }
        }

    
        updateBestSoFarTour();
    }
    public double[][] getPheromoneMatrix(){
        return pheromone;
    }


}
