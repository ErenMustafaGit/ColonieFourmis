package Fourmis;

import java.util.ArrayList;

public class Node2D extends Node {

    private ArrayList<Node2D> voisins;
    private State nodeState;
    private int food;
    private ArrayList<Pheromone> pheromoneList;

    /**
     * Créé un noeud
     */
    public Node2D(){
        nodeState = State.FREE;
        voisins = new ArrayList<>();
        this.food = 0;
        this.pheromoneList = new ArrayList<>();
    }

    /**
     * permet d'obtenir l'état du noeud
     * @return retourne le STATE du noeud
     */
    public State getNodeState(){
        return this.nodeState;
    }

    /**
     * Permet d'établir l'état du noeud
     * @param state nouveau état du noeud
     */
    public void setNodeState(State state){
        this.nodeState = state;
    }

    /**
     * Ajoute le noeud en paramètre à l'ArrayList "voisins"
     * @param node2D : Noeud à ajouter à la liste
     */
    public void addNoeudVoisin(Node2D node2D){
        this.voisins.add(node2D);
    }

    /**
     * Permet d'obtenir les voisins d'un noeud
     * @return retourne une ArrayList de noeud
     */
    @Override
    public ArrayList<Node> getVoisins(){
        return new ArrayList<>(this.voisins);
    }

    /**
     * Permet d'obtenir les voisins libres du noeud
     * @return retourne une ArrayList de noeud
     */
    @Override
    public ArrayList<Node> getFreeVoisins(){
        ArrayList<Node> temp = new ArrayList<>();
        for(Node n : this.voisins){
            if(n.getNodeState() != State.OBSTACLE){
                temp.add(n);
            }
        }
        return temp;
    }


    /**
     * Permet de definir la quantité de nourriture dans le noeud
     * @param amount : quantité de nourriture à placer dans le noeud.
     */
    public void setFood(int amount){
        this.food = amount;
    }


    /**
     * Permet d'obtenir la quantité de nourriture sur le noeud.
     * @return retourne une quantité de nourriture
     */
    public int getFood(){
        return this.food;
    }

    /**
     * Permet d'ajouter un phéromone à liste de phéromone (pheromoneList)
     * @param pheromone : phéromone à ajouter
     */
    public void addPheromone(Pheromone pheromone){
        if(this.getNodeState() != State.ANTHILL)
            this.pheromoneList.add(pheromone);
    }

    /**
     * Permet d'obtenir les phéromones du noeud
     * @return retourne une ArrayList de pheromone
     */
    public ArrayList<Pheromone> getPheromone(){
        return new ArrayList<>(pheromoneList);
    }

    /**
     * Permet de supprimer les phéromones qui ont une quantité de 0 de la liste des phéromones
     */
    public void updatePheromone() {
        //Liste temporaire qui contiendra que les phéromones encore existant (+ de 0 de quantité)
        ArrayList<Pheromone> tempList = new ArrayList<>();
        for (Pheromone pheromone : getPheromone()) {
            if (pheromone.getQuantity() != 0){
                tempList.add(pheromone);
            }

        }
        this.pheromoneList = new ArrayList<>(tempList);
    }


}


