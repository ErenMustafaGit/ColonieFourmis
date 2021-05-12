package Fourmis;

import java.util.ArrayList;

public class Noeud {

    public enum STATE {
        FREE,
        OBSTACLE,
        ANTHILL //Fourmilliere
    }

    private ArrayList<Noeud> voisins;
    private STATE noeudState;

    public Noeud(){
        noeudState = STATE.FREE;
        voisins = new ArrayList<>();
    }

    public STATE getNoeudState(){
        return this.noeudState;
    }
    public void setNoeudState(STATE state){
        this.noeudState = state;
    }

    public ArrayList<Noeud> getVoisins(){
        return new ArrayList<>(this.voisins);
    }
    public ArrayList<Noeud> getFreeVoisins( ){
        ArrayList<Noeud> freeVoisins = new ArrayList<>();
        for(Noeud voisin : this.voisins){
            if(this.noeudState == STATE.FREE)
                freeVoisins.add(voisin);
        }
        return new ArrayList<>(freeVoisins);
    }
}


