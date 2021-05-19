package Fourmis;

public class Queen extends Ant {
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

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldier soldier = new Soldier(this.getPosition(), this);
        }
    }
}
