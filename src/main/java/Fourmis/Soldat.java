package Fourmis;

import java.util.ArrayList;

public class Soldat extends Fourmis{

    public Soldat(Noeud noeud) {
        super(noeud);
    }

    @Override
    public void move() {
        Noeud position = this.getPosition();
        ArrayList<Noeud> freeVoisins = position.getFreeVoisins();

    }
}
