package Fourmis;

import java.io.IOException;
import java.util.BitSet;

public interface AntFacadeController {

    /**
     * Fixe les paramètres de l'application.
     * @param evaporationParam rythme d'évaporation des phéromones. La valeur par défaut est 1 par itération.
     * @param foodParam quantité maximale de nourriture que peut transporter une fourmi ouvrière.
     * @param pheromoneParam quantité de phéromone déposée par les fourmis lors de leur passage dans une cellule.
     */
    void setParameters( int evaporationParam, int foodParam, int pheromoneParam );

    /**
     * Créé la grille sur laquelle se déplacent les fourmis.
     * @param width largeur de la grille
     * @param height hauteur de la grille
     */
    void createGrid( int width, int height );

    /**
     * Place un obstacle dans une cellule de la grille.
     * @param row ordonnée de la cellule
     * @param column abscisse de la cellule
     */
    void putObstacle(int row, int column);

    /**
     * Place de la nourriture dans une cellule de la grille.
     * @param row ordonnée de la cellule
     * @param column abscisse de la cellule
     * @param quantity quantité de nourriture déposée
     */
    void putFood( int row, int column, int quantity);

    /**
     * Créé une fourmilière dans une cellule de la grille
     * @param row ordonnée de la cellule
     * @param column abscisse de la cellule
     */
    void createColony(int row, int column);

    /**
     * Créé un ensemble de fourmis soldates
     * @param amount nombre de soldats à créer
     */
    void createSoldiers(int amount);

    /**
     * Créé un ensemble de fourmis ouvrières
     * @param amount nombre d'ouvrière's à créer
     */
    void createWorkers( int amount );

    /**
     * Créé le fichier d'historique des fourmis.
     * @param antLogFile nom du fichier (avec extension) pour l'enregistrement des états des fourmis
     */
    void setAntFile(String antLogFile);


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
    BitSet[][] play( int duration, boolean record ) throws IOException;



}
