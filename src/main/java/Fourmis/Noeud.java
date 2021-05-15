package Fourmis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ForkJoinPool;

public class Noeud {

    public enum STATE {
        FREE,
        OBSTACLE,
        ANTHILL //Fourmilliere
    }

    private ArrayList<Noeud> voisins;
    private STATE noeudState;
    private ArrayList<Fourmis> fourmi_noeud;

    /**
     * Créé un noeud
     */
    public Noeud(){
        noeudState = STATE.FREE;
        voisins = new ArrayList<>();
        fourmi_noeud = new ArrayList<>();
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
        ArrayList<Noeud> freeVoisins = new ArrayList<>();
        for(Noeud voisin : this.voisins){
            if(this.noeudState == STATE.FREE)
                freeVoisins.add(voisin);
        }
        return new ArrayList<>(freeVoisins);
    }

}


