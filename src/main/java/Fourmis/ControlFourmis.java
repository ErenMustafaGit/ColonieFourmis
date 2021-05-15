package Fourmis;

import java.util.*;

public class ControlFourmis implements AntFacadeController {
    private Graphe graphe;
    private ArrayList<Fourmis> liste_fourmis_all = new ArrayList<>();

    /**
     * Fixe les paramètres de l'application.
     * @param evaporationParam rythme d'évaporation des phéromones. La valeur par défaut est 1 par itération.
     * @param foodParam quantité maximale de nourriture que peut transporter une fourmi ouvrière.
     * @param pheromoneParam quantité de phéromone déposée par les fourmis lors de leur passage dans une cellule.
     */
    @Override
    public void setParameters(int evaporationParam, int foodParam, int pheromoneParam) {
        //V2
    }

    /**
     * Permet de créé un graphe, respectant une taille donné (donc une grille)
     * @param width : largeur de la grille
     * @param height : hauteur de la grille
     */
    @Override
    public void createGrid(int width, int height) {
        this.graphe = new Graphe(width, height);
    }

    /**
     * Permet de placer un obstacle à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    @Override
    public void putObstacle(int row, int column) {
        this.graphe.putObstacle(row, column);
    }

    /**
     * Place de la nourriture dans une cellule de la grille.
     * @param row ordonnée de la cellule
     * @param column abscisse de la cellule
     * @param quantity quantité de nourriture déposée
     */
    @Override
    public void putFood(int row, int column, int quantity) {
        //V2
    }

    /**
     * Place la reine à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    @Override
    public void createColony(int row, int column) {
        Reine reine = new Reine(this.graphe.getNoeud(row,column));
        this.graphe.createColony(row, column);
        liste_fourmis_all.add(reine);

    }

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    @Override
    //à reverifier
    public void createSoldiers(int amount) {
        ArrayList<Fourmis> temp_soldat = new ArrayList<>();
        for(Fourmis fourmis : this.liste_fourmis_all){
            if(fourmis instanceof Reine){
                for(int i = 0; i < amount; i++){
                    Soldat s = new Soldat(fourmis.getPosition());
                    Noeud n = fourmis.getPosition();
                    //n.addFourmiNoeud(s);
                    temp_soldat.add(s);
                }
            }
        }
        liste_fourmis_all.addAll(temp_soldat);
    }

    /**
     * Créé un ensemble de fourmis ouvrières
     * @param amount nombre d'ouvrière's à créer
     */
    @Override
    public void createWorkers(int amount) {
        //V2
    }

    /**
     * Créé le fichier d'historique des fourmis.
     * @param antLogFile nom du fichier (avec extension) pour l'enregistrement des états des fourmis
     */
    @Override
    public void setAntFile(String antLogFile) {
        //V2
    }

    /**
     * Déclenche des itérations.
     * @param duration nombre d'itérations à effectuer
     * @return un tableau de vecteurs de 5 bits. La dimension du tableau est celle de la grille.
     *  - le bit n° 0 vaut true si le noeud correspondant de la grille abrite la fourmilière ;
     *  - le bit n° 1 vaut true si le noeud est occupé par un obstacle ;
     *  - le bit n° 2 vaut true s'il y a au moins une fourmi-soldat sur le noeud ;
     *  - le bit n° 3 vaut true s'il y a au moins une fourmi-ouvrière sans nourriture sur le noeud ;
     *  - le bit n° 4 vaut true s'il y a au moins une ouvrière portant de la nourriture sur le noeud.
     *  - le bit n° 5 vaut true s'il y a de la nourriture sur le noeud ;
     *  - le bit n° 6 vaut true s'il y a des phéromones sur le noeud.
     */
    @Override
    public BitSet[][] play(int duration, boolean record) {
        BitSet[][] bit_play = new BitSet[graphe.getWidth()][graphe.getHeight()];

        //Déplacement des fourmis pour chaque itération
        for(int iteration = 0 ; iteration < duration; iteration++){
            for(Fourmis fourmis : this.liste_fourmis_all){
                if(fourmis instanceof Soldat){
                    fourmis.move();
                }
            }
        }

        //Remplissage
        for(int i = 0; i < graphe.getWidth(); i++){
            for(int j = 0; j < graphe.getHeight(); j++){
                BitSet bits = new BitSet(7);
                bit_play[i][j] = bits;
            }
        }

        //
        for(int row = 0; row < graphe.getWidth(); row++){
            for(int column = 0; column < graphe.getHeight(); column++){

                //Présence de colonnie
                if(graphe.getNoeud(row, column).getNoeudState() == Noeud.STATE.ANTHILL)
                    bit_play[row][column].set(0, true);

                    //Présence d'obstacle
                else if (graphe.getNoeud(row, column).getNoeudState() == Noeud.STATE.OBSTACLE)
                    bit_play[row][column].set(1, true);

                //Présence de soldat
                /*
                int compteur_soldat = 0;
                for(Fourmis fourmis : graphe.getNoeud(row, column).getFourmiNoeud()){
                    if(fourmis instanceof Soldat)
                        compteur_soldat++;
                }
                if(compteur_soldat > 0)
                    bit_play[row][column].set(2, true);

                 */
                // la suite dans le v2
            }
        }

        return bit_play;
    }
}
