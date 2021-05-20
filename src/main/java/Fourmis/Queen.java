package Fourmis;

public class Queen extends Ant {
    private int pheromoneQuantity = 0;
    private int collectCapicty = 0;

    /**
     * Créé une reine à partir d'un noeud donné
     * @param node : Noeud ou la reine sera créé
     */
    public Queen(Node node) {
        super(node);
        super.colony = this;
        node.setNodeState(Node.STATE.ANTHILL);
    }


    /**
     * La reine ne peu pas bouger
     */
    @Override
    public void move() {
    }

    public void setCollectCapacity(int capacity){
        this.collectCapicty = capacity;
    }

    public void setPheromoneQuantity(int pheromoneQuantity){
        this.pheromoneQuantity = pheromoneQuantity;
    }

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldier soldier = new Soldier(this.getPosition(), this);
        }
    }

    public void createWorkers(int amount){
        for(int i = 0; i<amount; i++){
            Worker worker = new Worker(this.getPosition(), this);
        }
    }
}
