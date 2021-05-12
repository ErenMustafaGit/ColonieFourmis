package Fourmis;
import projet.v2.AntFacadeController;

import java.util.ArrayList;
import java.util.BitSet;

public class ControlFourmis implements AntFacadeController {
    private Graphe graphe;
    private ArrayList<Fourmis> fourmis;

    @Override
    public void setParameters(int evaporationParam, int foodParam, int pheromoneParam) {
        //V2
    }

    @Override
    public void createGrid(int width, int height) {
        this.graphe = new Graphe(width, height);
    }

    @Override
    public void putObstacle(int row, int column) {
        this.graphe.putObstacle(row, column);
    }

    @Override
    public void putFood(int row, int column, int quantity) {
        //V2
    }

    @Override
    public void createColony(int row, int column) {
        Reine reine = new Reine(this.graphe.getNoeud(row,column));
        fourmis.add(reine);
    }

    @Override
    public void createSoldiers(int amount) {

        //A VOIR !!

        for(Fourmis fourmis : this.fourmis){
            if(fourmis instanceof Reine){
                ((Reine) fourmis).createSoldiers(amount);
            }
        }
    }

    @Override
    public void createWorkers(int amount) {
        //V2
    }

    @Override
    public void setAntFile(String antLogFile) {
        //V2
    }

    @Override
    public BitSet[][] play(int duration, boolean record) {
        return new BitSet[0][];
    }
}
