package Fourmis;

import java.util.ArrayList;
import java.util.Random;

public class Soldat extends Fourmis{

    public Soldat(Noeud noeud) {
        super(noeud);
    }

    @Override
    public void move() {
        Noeud position = this.getPosition();
        ArrayList<Noeud> freeVoisins = position.getFreeVoisins();
        Random rnd = new Random();

        //Prend un noeud au hasard parmis ceux de libre
        Noeud direction = freeVoisins.get(rnd.nextInt(freeVoisins.size()));

        //Va en direction de ce noeud là
        this.setPosition(direction);
    }
}
