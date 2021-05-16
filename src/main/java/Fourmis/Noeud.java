package Fourmis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ForkJoinPool;

public class Noeud {

    /**
     * Différent état d'un noeud
     */
    public enum STATE {
        FREE,
        OBSTACLE,
        ANTHILL //Fourmilliere
    }

    private ArrayList<Noeud> voisins;
    private STATE noeudState;


    /**
     * Créé un noeud
     */
    public Noeud(){
        noeudState = STATE.FREE;
        voisins = new ArrayList<>();
    }

    /**
     * permet d'obtenir l'état du noeud
     * @return retourne un STATE
     */
    public STATE getNoeudState(){
        return this.noeudState;
    }

    /**
     * Permet d'établir l'état du noeud
     * @param state nouveau état du noeud
     */
    public void setNoeudState(STATE state){
        this.noeudState = state;
    }

    public void addNoeudVoisin(Noeud noeud){
        this.voisins.add(noeud);
    }

    /**
     * Permet d'obtenir les voisins d'un noeud
     * @return retourne une Arrayliste de noeud
     */
    public ArrayList<Noeud> getVoisins(){
        return new ArrayList<>(this.voisins);
    }

    /**
     * Permet d'obtenir les voisins libre du noeud
     * @return retourne une ArrayListe de noeud
     */
    public ArrayList<Noeud> getFreeVoisins(){
        ArrayList<Noeud> temp = new ArrayList<>();
        for(Noeud n : this.voisins){
            if(n.getNoeudState() == STATE.FREE){
                temp.add(n);
            }
        }
        return temp;
    }

}


