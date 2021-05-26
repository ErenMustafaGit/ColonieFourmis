package Fourmis;

public class AntHill extends Ant {
    private int pheromoneQuantity = 0;
    private int collectCapicty = 0;

    /**
     * Créé une reine à partir d'un noeud donné
     * @param node : Noeud ou la reine sera créé
     */
    public AntHill(Node node) {
        super(node);
        super.colony = this;
        node.setNodeState(Node.STATE.ANTHILL);
    }


    /**
     * La reine ne peut pas bouger
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

    public int getPheromoneQuantity(){
        return this.pheromoneQuantity;
    }

    public int getCollectCapicty(){
        return this.collectCapicty;
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
