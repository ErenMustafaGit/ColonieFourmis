package Fourmis;
import projet.v2.AntFacadeController;

import java.util.*;

public class ControlFourmis implements AntFacadeController {
    private Graphe graphe;
    private ArrayList<Fourmis> liste_fourmis_all;
    private ArrayList<Fourmis> liste_fourmis_noeud;

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
        liste_fourmis_all.add(reine);
    }

    @Override
    public void createSoldiers(int amount) {
        for(Fourmis fourmis : this.liste_fourmis_all){
            if(fourmis instanceof Reine){
                //((Reine) fourmis).createSoldiers(amount);
                for(int i = 0; i < amount; i++){
                    Soldat s = new Soldat(fourmis.getPosition());
                    liste_fourmis_all.add(s);
                }
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
        BitSet[][] bit_play = new BitSet[graphe.getWidth()][graphe.getHeight()];

        //Remplissage
        for(int i = 0; i < graphe.getWidth(); i++){
            for(int j = 0; j < graphe.getHeight(); j++){
                BitSet bits = new BitSet(6);
                bits.set(0);
                bit_play[i][j] = bits;
            }
        }

        //Chaque itération
        for(int iteration = 0 ; iteration < duration; iteration++){
            for(int row = 0; row < graphe.getWidth(); row++){
                for(int column = 0; column < graphe.getHeight(); column++){

                    //Présence de colonnie
                    if(graphe.getNoeud(row, column).getNoeudState() == Noeud.STATE.ANTHILL)
                        bit_play[row][column].set(0, true);

                    //Présence d'obstacle
                    else if (graphe.getNoeud(row, column).getNoeudState() == Noeud.STATE.OBSTACLE)
                        bit_play[row][column].set(1, true);

                    //Présence de soldat
                    int compteur_soldat = 0;
                    for(Fourmis fourmis : this.liste_fourmis_noeud){
                        if(fourmis instanceof Soldat)
                            compteur_soldat++;
                    }
                    if(compteur_soldat > 0)
                        bit_play[row][column].set(2, true);

                    // la suite dans le v2
                }
            }
        }
        return bit_play;
    }
}
