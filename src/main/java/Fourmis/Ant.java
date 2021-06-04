package Fourmis;

public abstract class Ant {
    private Node position;
    protected AntHill colony;

    /**
     * Créé une fourmis à un noeud donné
     * @param node : emplacement de la fourmis
     * @param colony : Reine qui a crée cette fourmis
     */
    public Ant(Node node, AntHill colony){
        this.position = node;
        this.colony = colony;
    }

    public Ant(Node node2D){
        this.position = node2D;
    }


    /**
     * Permet de faire déplacer une fourmis
     */
    public abstract void move();

    /**
     * Récupère la position d'une fourmis
     * @return retourne le noeud ou ce trouve la fourmis
     */
    public Node getPosition(){
        return this.position;
    }

    /**
     * Etabli la position d'une fourmis
     * @param node2D : nouvelle position
     */
    public void setPosition(Node node2D){
        this.position = node2D;
    }
}
