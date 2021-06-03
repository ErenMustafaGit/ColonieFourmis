package Fourmis;

import com.sun.nio.sctp.PeerAddressChangeNotification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Node implements Comparable<Node> {

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
    private ArrayList<Pheromone> pheromoneList;

    /**
     * Créé un noeud
     */
    public Node(){
        nodeState = STATE.FREE;
        voisins = new ArrayList<>();
        this.food = 0;
        this.pheromoneList = new ArrayList<>();
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
        if(this.getNodeState() != STATE.ANTHILL)
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


    /**
     *
     * @param node : Le noeud à comparer avec l'instance présente
     * @return
     *      o : 1 si l'instance a plus de phéromone
     *      o : 0 si ils ont la quantité de phéromone identique
     *      o : -1 si node a plus de phéromone
     */
    @Override
    public int compareTo(Node node) {
        ArrayList<Pheromone> pheromoneList = new ArrayList<>(node.getPheromone());
        int quantityPheromone = 0;
        //Ne prend pas en compte les différentes colony, cad que lorsque la fourmis
        // appel les noeuds voisins, les pheromones ne sont pas distinct
        for(Pheromone pheromone : pheromoneList){
            quantityPheromone += pheromone.getQuantity();
        }

        ArrayList<Pheromone> pheromoneListThis = new ArrayList<>(this.getPheromone());
        int quantityPheromoneThis = 0;
        for(Pheromone pheromone : pheromoneListThis){
            quantityPheromoneThis += pheromone.getQuantity();
        }

        if(quantityPheromoneThis > quantityPheromone)
            return 1;
        else if (quantityPheromoneThis < quantityPheromone)
            return -1;
        else
            return 0;
    }
}


