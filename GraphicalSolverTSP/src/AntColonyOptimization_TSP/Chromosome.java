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

public class Chromosome {
    private int[] genes;
    private int noGenes;
    private int[][] dist;
    Chromosome(int noGenes, int[][] d){
        this.noGenes = noGenes;
        genes = new int[noGenes+1];
        dist = d;
    }
    public int[] getTour(){
        return genes;
    }
    public int getNoGenes(){
        return noGenes;
    }
    public int getGene(int idx){
        return genes[idx];
    }
    public void setGene(int idx, int val){
        genes[idx] = val;
    }
    public void mutate(){
        Random rand = new Random();
        int idx1 = rand.nextInt(noGenes);
        int idx2 = rand.nextInt(noGenes);
        int swap = genes[idx1];
        genes[idx1] = genes[idx2];
        genes[idx2] = swap;
        genes[noGenes] = genes[0];
    }
    public void shuffle(){
        Random rand = new Random();
        for(int i = 0; i < noGenes; i++){
            genes[i] = i;
        }

        for(int i = 0; i < noGenes; i++){
            int idx = rand.nextInt(noGenes);
            int swap = genes[i];
            genes[i] = genes[idx];
            genes[idx] = swap;
        }
        genes[noGenes] = genes[0];
    }
    public void crossover(final Chromosome father, final Chromosome offspring1, final Chromosome offspring2){
        offspring1.genes[0] = this.genes[0];
        offspring2.genes[0] = father.genes[0];
        
        boolean visited1[], visited2[];
        visited1 = new boolean[noGenes];
        visited2 = new boolean[noGenes];

        for(int i = 0; i < noGenes; i++){
            visited1[i] = false;
            visited2[i] = false;
        }

        visited1[offspring1.genes[0]] = true;
        visited2[offspring2.genes[0]] = true;

        for(int i = 1; i < noGenes; i++){
            int prevNode = offspring1.genes[i-1];
            int node1 = 0, node2 = 0;
            for(int j = 0; j < noGenes; j++){
                if(this.genes[j] == prevNode)
                    node1 = this.genes[j+1];
                if(father.genes[j] == prevNode)
                    node2 = father.genes[j+1];

            }
            if((!visited1[node1]) && (!visited1[node2])){
                if(dist[prevNode][node1] < dist[prevNode][node2]){
                    offspring1.genes[i] = node1;
                    visited1[node1] = true;
                }
                else{
                    offspring1.genes[i] = node2;
                    visited1[node2] = true;
                }
                continue;
            }
            if((!visited1[node1]) && visited1[node2]){
                offspring1.genes[i] = node1;
                visited1[node1] = true;
                continue;
            }
            if((!visited1[node2]) && visited1[node1]){
                offspring1.genes[i] = node2;
                visited1[node2] = true;
                continue;
            }
            if(visited1[node1] && visited1[node2]){
                Random rand = new Random();
                int node = rand.nextInt(noGenes);
                while(visited1[node]){
                    node = rand.nextInt(noGenes);
                }
                visited1[node] = true;
                offspring1.genes[i] = node;
                continue;
            }
        }
        //===================================================
        for(int i = 1; i < noGenes; i++){
            int prevNode = offspring2.genes[i-1];
            int node1 = 0, node2 = 0;
            for(int j = 0; j < noGenes; j++){
                if(this.genes[j] == prevNode)
                    node1 = this.genes[j+1];
                if(father.genes[j] == prevNode)
                    node2 = father.genes[j+1];

            }
            if((!visited2[node1]) && (!visited2[node2])){
                if(dist[prevNode][node1] < dist[prevNode][node2]){
                    offspring2.genes[i] = node1;
                    visited2[node1] = true;
                }
                else{
                    offspring2.genes[i] = node2;
                    visited2[node2] = true;
                }
                continue;
            }
            if((!visited2[node1]) && visited2[node2]){
                offspring2.genes[i] = node1;
                visited2[node1] = true;
                continue;
            }
            if((!visited2[node2]) && visited2[node1]){
                offspring2.genes[i] = node2;
                visited2[node2] = true;
                continue;
            }
            if(visited2[node1] && visited2[node2]){
                Random rand = new Random();
                int node = rand.nextInt(noGenes);
                while(visited2[node]){
                    node = rand.nextInt(noGenes);
                }
                visited2[node] = true;
                offspring2.genes[i] = node;
                continue;
            }

        }
        offspring1.genes[noGenes] = offspring1.genes[0];
        offspring2.genes[noGenes] = offspring2.genes[0];
        

    }
    @Override
    public String toString(){
        String str = "[ ";
        for(int i = 0; i < noGenes+1; i++){
            str+=genes[i]+", ";
        }
        str+=" ]";
        return str;
    }
    public int getCost(){
        int sum = 0;

        for(int i = 0; i < noGenes; i++){
            sum+=dist[genes[i]][genes[i+1]];
        }
        return sum;
    }
    public boolean equals(Chromosome c){
        for(int i = 0; i < noGenes + 1; i++)
            if(c.genes[i] != genes[i])
                return false;
        return true;
    }
    public boolean isAnomaly(){
        for(int i = 0; i < noGenes-1; i++){
            for(int j = i+1; j < noGenes; j++)
                if(genes[i] == genes[j])
                    return true;
        }
        if(genes[0] != genes[noGenes])
            return true;
        return false;
    }
}

