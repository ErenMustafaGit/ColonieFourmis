package Fourmis;

import java.util.ArrayList;
import java.util.Random;

public class Soldier extends Ant {

    /**
     * Créé un soldat à partir d'un noeud
     * @param node : emplacement de la reine
     */
    public Soldier(Node node, Queen colony) {
        super(node, colony);
    }

    /**
     * Déplace la fourmis soldat en fonction de ses noeud voisin.
     */
    @Override
    public void move() {
        Node position = this.getPosition();
        ArrayList<Node> freeVoisins = new ArrayList<>(position.getFreeVoisins());
        //Si la fourmis ce trouve sur un noeud qui possède une liste de NoeudVoisin différent de 0,
        //alors elle peu bouger, sinon elle ne fait rien.
        if(freeVoisins.size() != 0){
            Random rnd = new Random();

            //Prend un noeud au hasard parmis ceux de libre
            Node direction = freeVoisins.get(rnd.nextInt(freeVoisins.size()));

            //Va en direction de ce noeud là
            this.setPosition(direction);
        }else{
            System.out.println("Bloqué");
        }
    }
}
