package Fourmis;

import java.util.ArrayList;
import java.util.List;

public class Graphe {
    private int width;
    private int height;
    private List<Noeud> noeudList;


    /**
     * Créé un graphe, le remplis de noeud, puis ajoute les noeuds voisins des noeuds
     * @param width : longueur
     * @param height : hauteur
     */
    public Graphe(Integer width, Integer height){
        noeudList = new ArrayList<>();
        this.width = width;
        this.height = height;
        //Ajout des noeuds dans le graphe
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Noeud n = new Noeud();
                noeudList.add(n);
            }
        }

        //Instaciation des liens entre les Noeuds -> Liste de voisins
        for(int x = 0; x < height - 1;x++){
            for(int y = 0;y<width - 1;y++){
                //Récuperation Noeud actuel
                Noeud n = this.getNoeud(x,y);

                //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
                Noeud nRight = this.getNoeud(x+1,y);
                //Noeud nRight = this.graphe_tab.get(x+y*width+1);


                //Récuperation Noeud se trouvant en bas du Noeud actuel (n)
                Noeud nDown = this.getNoeud(x,y+1);
                //Noeud nDown = this.graphe_tab.get(x+(y+1)*width);

                //Ajout dans le noeud actuel (n), les voisins du bas et de droite
                n.addNoeudVoisin(nRight);
                n.addNoeudVoisin(nDown);

                //Ajout dans les noeuds de droite et du bas, le voisin : noeud actuel (n)
                nRight.addNoeudVoisin(n);
                nDown.addNoeudVoisin(n);
            }
        }


        //Noeud de tout à droite
        for(int x = 0; x<width-1; x++){
            //Récuperation Noeud actuel
            Noeud n = this.getNoeud(x,height-1);

            //Récuperation Noeud se trouvant en bas du Noeud actuel (n)
            Noeud nDown = this.getNoeud(x+1,height-1);

            //Ajout dans le noeud actuel (n), le voisins du bas
            n.addNoeudVoisin(nDown);

            //Ajout dans le noeuds du bas, le voisin : noeud actuel (n)
            nDown.addNoeudVoisin(n);
        }

        //Noeud de tout en bas
        for(int y = 0; y<height-1; y++){
            //Récuperation Noeud actuel
            Noeud n = this.getNoeud(width-1,y);

            //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
            Noeud nRight = this.getNoeud(width-1,y+1);

            //Ajout dans le noeud actuel (n), le voisins de droite
            n.addNoeudVoisin(nRight);

            //Ajout dans le noeuds de droite, le voisin : noeud actuel (n)
            nRight.addNoeudVoisin(n);
        }
    }


    /**
     * Permet d'obtenir un noeud à partir d'une coordonné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     * @return retourne un Noeud
     */
    public Noeud getNoeud(Integer row, Integer column){
        return noeudList.get(column + row * width);
    }

    /**
     * Permet de placer un obstacle à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    public void putObstacle(Integer row, Integer column){
        Noeud n = noeudList.get(column+ row * width);

        // System.out.println(row + ":"+column + "\nstate : " + n.getNoeudState().toString());
        if(n.getNoeudState() != Noeud.STATE.ANTHILL){
            n.setNoeudState(Noeud.STATE.OBSTACLE);
            // System.out.println("new state : " + n.getNoeudState().toString()+"\n");
        }

        if(row == 0 && column == 0){
            throw new IllegalArgumentException("Impossible de placer un obstacle sur une fourmillière");
        }

    }

    /**
     * Place la reine à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */

    public void createColony(Integer row,Integer column){
        Noeud n = getNoeud(row , column);
        //n.setNoeudState(Noeud.STATE.ANTHILL);
        // System.out.println(row + ":"+column + " " + n.getNoeudState().toString() + "\n");
        Reine r = new Reine(n);
    }

    /**
     * Permet de récuperer les noeuds contenue dans le graphe
     * @return retourne une Liste de Noeud
     */
    public List<Noeud> getNoeudList(){
        return this.noeudList;
    }

    /**
     * Renvoie la longueur de la grille du graphe
     * @return renvoie la longueur de la grille du graphe
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Renvoie la hauteur de la grille du graphe
     * @return renvoie la hauteur de la grille du graphe
     */
    public int getHeight(){
        return this.height;
    }

}
