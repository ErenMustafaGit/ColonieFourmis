package Fourmis;

public class Pheromone {
    private int quantity;
    private Queen colony;

    public Pheromone(int quantity, Queen colony){
        this.quantity = quantity;
        this.colony = colony;
    }

    public int getQuantity() {
        return this.quantity;
    }

}
