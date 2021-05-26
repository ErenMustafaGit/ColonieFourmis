package Fourmis;

public class Pheromone {
    private int quantity;
    private AntHill colony;

    public Pheromone(int quantity, AntHill colony){
        this.quantity = quantity;
        this.colony = colony;
    }

    public int getQuantity() {
        return this.quantity;
    }

}
