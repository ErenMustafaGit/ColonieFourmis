package Fourmis;

import java.util.ArrayList;

public class Reine extends Fourmis{
    /**
     * Créé une reine à partir d'un noeud donné
     * @param noeud : Noeud ou la reine sera créé
     */
    public Reine(Noeud noeud) {
        super(noeud);
        noeud.setNoeudState(Noeud.STATE.ANTHILL);
    }

    /**
     * La reine ne peu pas bouger
     */
    @Override
    public void move() {
    }

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldat soldat = new Soldat(this.getPosition());
        }
    }
}
