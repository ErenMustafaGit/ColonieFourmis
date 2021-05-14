package Fourmis;

public class Reine extends Fourmis{
    //Avec ça qui prend le position du noeud de la reine
    Noeud emplacements;

    public Reine(Noeud noeud) {
        super(noeud);
        this.emplacements = noeud;
        noeud.setNoeudState(Noeud.STATE.ANTHILL);
    }

    @Override
    public void move() {
    }


    //Puis créé la fourmis soldier à l'emplacement de la reine, donc à la fourmillière
    public void createSoldiers(int amount) {
        for(int i = 0; i<amount; i++){
            Soldat soldat = new Soldat(this.getPosition());
            //pour créé le soldat sur l'emplacement de la reine (donc la fourmillière
            soldat.setPosition(emplacements);
        }
    }
}
