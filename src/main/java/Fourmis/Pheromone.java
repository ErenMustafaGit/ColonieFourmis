package Fourmis;

public class Pheromone {
    private int quantity;
    private AntHill colony;

    /**
     * Créé une phéromone en fonction de la quantité et de la colonnie.
     * @param quantity la quantité de phéromone
     * @param colony l'origine de la phéromone (provenance de quel reine)
     */
    public Pheromone(int quantity, AntHill colony){
        this.quantity = quantity;
        this.colony = colony;
    }

    /**
     * Permet de récuperer la quantité de phéromone établis
     * @return retourne la quantité de phéromone
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Permet d'établir la quantité de phéromone
     * @param quantity quantité qui vas être assigné à quantité de phéromone de la Phéromone
     */
    public void setQuantity(int quantity) {
        if(quantity > 0){
            this.quantity = quantity;
        }
        else{
            this.quantity = 0;
        }
    }

}
