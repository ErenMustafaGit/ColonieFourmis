package Fourmis;

import java.util.ArrayList;

public abstract class Fourmis {
    private Noeud position;

    /**
     * Créé une fourmis à un noeud donné
     * @param noeud : emplacement de la fourmis
     */
    public Fourmis(Noeud noeud){
        this.position = noeud;
    }

    /**
     * Permet de faire déplacer une fourmis
     */
    public abstract void move();

    /**
     * Récupère la position d'une fourmis
     * @return retourne le noeud ou ce trouve la fourmis
     */
    public Noeud getPosition(){
        return this.position;
    }

    /**
     * Etabli la position d'une fourmis
     * @param noeud : nouvelle position
     */
    public void setPosition(Noeud noeud){
        this.position = noeud;
    }

}
