package Fourmis;

public class Reine extends Fourmis{

    public Reine(Noeud noeud) {
        super(noeud);
        noeud.setNoeudState(Noeud.STATE.ANTHILL);
    }

    @Override
    public void move() {
    }

    //A VOIR !
    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldat soldat = new Soldat(this.getPosition());
        }
    }
}
