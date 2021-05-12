package Fourmis;

import java.util.ArrayList;

public abstract class Fourmis {
    private Noeud position;

    public Fourmis(Noeud noeud){
        this.position = noeud;
    }

    public abstract void move();

    public Noeud getPosition(){
        return this.position;
    }
    public void setPosition(Noeud noeud){
        this.position = noeud;
    }

}
