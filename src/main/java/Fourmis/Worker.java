package Fourmis;

import java.util.ArrayList;
import java.util.Random;

public class Worker extends Ant {
    private int foodCollected;
    /**
     * Créé une fourmis à un noeud donné
     *
     * @param node : emplacement de la fourmis
     */
    public Worker(Node node, AntHill colony) {
        super(node, colony);
    }

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
        }
    }

    public void collect(){
        int foodQuantity = this.getPosition().getFood();
        if(foodQuantity != 0){
            if(foodQuantity < this.colony.getCollectCapicty()){
                this.foodCollected = foodQuantity;
                this.getPosition().setFood(0);
            }
            else{
                this.foodCollected = this.colony.getCollectCapicty();
                this.getPosition().setFood(foodQuantity - this.foodCollected);
            }
        }

    }

    public void putPheromone(){
        Pheromone pheromone = new Pheromone(super.colony.getPheromoneQuantity(), this.colony);
        this.getPosition().addPheromone(pheromone);
    }
}
