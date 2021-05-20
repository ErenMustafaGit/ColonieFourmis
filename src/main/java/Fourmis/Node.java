package Fourmis;

import java.util.ArrayList;

public class Node {

    /**
     * Différent état d'un noeud
     */
    public enum STATE {
        FREE,
        OBSTACLE,
        ANTHILL //Fourmilliere
    }

    private ArrayList<Node> voisins;
    private STATE nodeState;
    private int food;

    /**
     * Créé un noeud
     */
    public Node(){
        nodeState = STATE.FREE;
        voisins = new ArrayList<>();
        this.food = 0;
    }

    public boolean isObstacle(){
        return this.nodeState == STATE.OBSTACLE;
    }
    /**
     * permet d'obtenir l'état du noeud
     * @return retourne le STATE du noeud
     */
    public STATE getNodeState(){
        return this.nodeState;
    }

    /**
     * Permet d'établir l'état du noeud
     * @param state nouveau état du noeud
     */
    public void setNodeState(STATE state){
        this.nodeState = state;
    }

    /**
     * Ajoute le noeud en paramètre à l'ArrayList "voisins"
     * @param node : Noeud à ajouter à la liste
     */
    public void addNoeudVoisin(Node node){
        this.voisins.add(node);
    }

    /**
     * Permet d'obtenir les voisins d'un noeud
     * @return retourne une ArrayList de noeud
     */
    public ArrayList<Node> getVoisins(){
        return new ArrayList<>(this.voisins);
    }

    /**
     * Permet d'obtenir les voisins libres du noeud
     * @return retourne une ArrayList de noeud
     */
    public ArrayList<Node> getFreeVoisins(){
        ArrayList<Node> temp = new ArrayList<>();
        for(Node n : this.voisins){
            if(n.getNodeState() != STATE.OBSTACLE){
                temp.add(n);
            }
        }
        return temp;
    }

    public void setFood(int amount){
        this.food = amount;
    }

    public int getFood(){
        return this.food;
    }

}


