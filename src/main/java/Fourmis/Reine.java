package Fourmis;

public class Reine extends Fourmis{
    //Avec ça qui prend le position du noeud de la reine
   // private Reine r;

    public Reine(Noeud noeud) {
        super(noeud);
        noeud.setNoeudState(Noeud.STATE.ANTHILL);
       // this.r = new Reine(noeud);
    }

    @Override
    public void move() {
    }


    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldat soldat = new Soldat(this.getPosition());
        }
    }
}
