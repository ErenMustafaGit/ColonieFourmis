package Fourmis;

public class AntHill extends Ant {
    private int pheromoneQuantity = 0;
    private int collectCapacity = 0;

    /**
     * Créé une reine à partir d'un noeud donné
     * @param node2D : Noeud ou la reine sera créé
     */
    public AntHill(Node node2D) {
        super(node2D);
        super.colony = this;
        node2D.setNodeState(State.ANTHILL);
    }


    /**
     * La reine ne peut pas bouger
     */
    @Override
    public void move() {
    }


    /**
     * Permet de définir la capcacitée de collecte pour une fourmis ouvrière
     * @param capacity valeur à attribuer pour définir la capacité de collecte
     */
    public void setCollectCapacity(int capacity){
        this.collectCapacity = capacity;
    }

    /**
     * Permet de définir la quantité de phéromone
     * @param pheromoneQuantity quantité qui vas définir la quantité de phéromone
     */
    public void setPheromoneQuantity(int pheromoneQuantity){
        this.pheromoneQuantity = pheromoneQuantity;
    }

    /**
     * Permet de récuperer la quantité de phéromone établi
     * @return retourne la quantité de phéromone établi au sein de la colonnie
     */
    public int getPheromoneQuantity(){
        return this.pheromoneQuantity;
    }

    /**
     * Permet de récuperer la capacité de collecte
     * @return retour la capacité de collecte définis
     */
    public int getCollectCapacity(){
        return this.collectCapacity;
    }

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldier soldier = new Soldier((Node2D) this.getPosition(), this);
        }
    }

    public void createWorkers(int amount){
        for(int i = 0; i<amount; i++){
            Worker worker = new Worker(this.getPosition(), this);
        }
    }
}
