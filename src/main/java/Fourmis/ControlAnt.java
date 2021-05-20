package Fourmis;

import java.util.*;

public class ControlAnt implements AntFacadeController {
    private Graph graph;
    private ArrayList<Ant> listeFourmis = new ArrayList<>();
    private int collectCapacity = 0;
    private int pheromoneQuantity = 0;

    /**
     * Fixe les paramètres de l'application.
     * @param evaporationParam rythme d'évaporation des phéromones. La valeur par défaut est 1 par itération.
     * @param foodParam quantité maximale de nourriture que peut transporter une fourmi ouvrière.
     * @param pheromoneParam quantité de phéromone déposée par les fourmis lors de leur passage dans une cellule.
     */
    @Override
    public void setParameters(int evaporationParam, int foodParam, int pheromoneParam) {
        //s'il n'y a pas de reine.
        if(listeFourmis.isEmpty()){
            this.collectCapacity = foodParam;
            this.pheromoneQuantity = pheromoneParam;
        }
        //si les reines sont déjà existente.
        else {
            for(Ant ant : listeFourmis){
                if(ant instanceof Queen){
                    ((Queen) ant).setCollectCapacity(foodParam);
                    ((Queen) ant).setPheromoneQuantity(pheromoneQuantity);
                }
            }
        }

    }

    /**
     * Permet de créé un graphe, respectant une taille donné (donc une grille)
     * @param width : largeur de la grille
     * @param height : hauteur de la grille
     */
    @Override
    public void createGrid(int width, int height) {
        this.graph = new Graph(width, height);
    }

    /**
     * Permet de placer un obstacle à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    @Override
    public void putObstacle(int row, int column) {
        this.graph.putObstacle(row, column);
    }

    /**
     * Place de la nourriture dans une cellule de la grille.
     * @param row ordonnée de la cellule
     * @param column abscisse de la cellule
     * @param quantity quantité de nourriture déposée
     */
    @Override
    public void putFood(int row, int column, int quantity) {
        Node n = this.graph.getNoeud(row, column);
        n.setFood(quantity);
    }

    /**
     * Place la reine à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    @Override
    public void createColony(int row, int column) {
        Queen queen = new Queen(this.graph.getNoeud(row,column));
        queen.setPheromoneQuantity(this.pheromoneQuantity);
        queen.setCollectCapacity(this.collectCapacity);
        listeFourmis.add(queen);
    }

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    @Override
    //à reverifier
    public void createSoldiers(int amount) {
        ArrayList<Ant> temp_soldat = new ArrayList<>();
        for(Ant ant : this.listeFourmis){
            //Si la fourmis est une reine (donc colonie)
            if(ant instanceof Queen){
                for(int i = 0; i < amount; i++){
                    Soldier s = new Soldier(ant.getPosition(), (Queen)ant);
                    temp_soldat.add(s);
                }
            }
        }
        listeFourmis.addAll(temp_soldat);
    }

    /**
     * Créé un ensemble de fourmis ouvrières
     * @param amount nombre d'ouvrière's à créer
     */
    @Override
    public void createWorkers(int amount) {

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
        //BitSet[][] bit_play = new BitSet[this.graphe.getWidth()][this.graphe.getHeight()];
        BitSet[][] bit_play = new BitSet[this.graph.getHeight()][this.graph.getWidth()];

        //Déplacement des fourmis pour chaque itération
        for(int iteration = 0 ; iteration < duration; iteration++){
            for(Ant ant : this.listeFourmis){
                if(ant instanceof Soldier){
                    ant.move();
                }
            }
        }


        for(int row = 0; row < this.graph.getHeight(); row++){
            for(int column = 0; column < this.graph.getWidth(); column++){
                bit_play[row][column]=new BitSet(7);
                //Présence de colonnie
                if(this.graph.getNoeud(row, column).getNodeState() == Node.STATE.ANTHILL)
                    bit_play[row][column].set(0, true);

                    //Présence d'obstacle
                else if (this.graph.getNoeud(row, column).getNodeState() == Node.STATE.OBSTACLE)
                    bit_play[row][column].set(1, true);

                //Présence de soldat
                int compteur_soldat = 0;
                for(Ant ant : this.listeFourmis){
                    if(ant instanceof Soldier && ant.getPosition() == this.graph.getNoeud(row, column)){
                        compteur_soldat++;
                        if(compteur_soldat > 0){
                            bit_play[row][column].set(2, true);
                        }
                    }
                }
                // la suite dans le v2
            }
        }

        return bit_play;
    }
}
