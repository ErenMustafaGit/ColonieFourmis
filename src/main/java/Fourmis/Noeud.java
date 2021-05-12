package Fourmis;

import java.util.ArrayList;

public class Noeud {
    private boolean free;
    private ArrayList<Noeud> voisins;

    public Noeud(){
        free = true;
        voisins = new ArrayList<>();
    }

    public boolean isFree(){
        return this.free;
    }
    public void setFree(boolean val){
        this.free = val;
    }

    public ArrayList<Noeud> getVoisins(){
        return new ArrayList<>(this.voisins);
    }
    public ArrayList<Noeud> getFreeVoisins( ){
        ArrayList<Noeud> freeVoisins = new ArrayList<>();
        for(Noeud voisin : this.voisins){
            if(voisin.isFree())
                freeVoisins.add(voisin);
        }
        return new ArrayList<>(freeVoisins);
    }
}
