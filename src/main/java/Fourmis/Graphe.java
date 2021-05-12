package Fourmis;

import java.util.ArrayList;
import java.util.List;

public class Graphe {
    private int width;
    private int height;
    private List<Noeud> noeudList;

    public Graphe(Integer width, Integer height){
        noeudList = new ArrayList<>();

        //Ajout des noeuds dans le graphe
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Noeud n = new Noeud();
                noeudList.add(n);
            }
        }

        //Instaciation des liens entre les Noeuds -> Liste de voisins
        for(int x = 0; x < width - 1;x++){
            for(int y = 0;y<height - 1;y++){
                //Récuperation Noeud actuel
                Noeud n = this.getNoeud(x,y);

                //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
                Noeud nRight = this.getNoeud(x+1,y);
                //Noeud nRight = this.graphe_tab.get(x+y*width+1);


                //Récuperation Noeud se trouvant en bas du Noeud actuel (n)
                Noeud nDown = this.getNoeud(x,y+1);
                //Noeud nDown = this.graphe_tab.get(x+(y+1)*width);

                //Ajout dans le noeud actuel (n), les voisins du bas et de droite
                n.getVoisins().add(nRight);
                n.getVoisins().add(nDown);

                //Ajout dans les noeuds de droite et du bas, le voisin : noeud actuel (n)
                nRight.getVoisins().add(n);
                nDown.getVoisins().add(n);
            }
        }


        //Noeud de tout à droite
        for(int x = 0; x<height-1; x++){
            //Récuperation Noeud actuel
            Noeud n = this.getNoeud(x,width-1);

            //Récuperation Noeud se trouvant en bas du Noeud actuel (n)
            Noeud nDown = this.getNoeud(x+1,width-1);

            //Ajout dans le noeud actuel (n), le voisins du bas
            n.getVoisins().add(nDown);

            //Ajout dans le noeuds du bas, le voisin : noeud actuel (n)
            nDown.getVoisins().add(n);
        }

        //Noeud de tout en bas
        for(int y = 0; y<width-1; y++){
            //Récuperation Noeud actuel
            Noeud n = this.getNoeud(height-1,y);

            //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
            Noeud nRight = this.getNoeud(height-1,y+1);

            //Ajout dans le noeud actuel (n), le voisins de droite
            n.getVoisins().add(nRight);

            //Ajout dans le noeuds de droite, le voisin : noeud actuel (n)
            nRight.getVoisins().add(n);
        }
    }



    public Noeud getNoeud(Integer row, Integer column){
        return noeudList.get(row + column * width);
    }

    public void putObstacle(Integer row, Integer column){
        Noeud n = noeudList.get(row + column * width);
        n.setNoeudState(Noeud.STATE.OBSTACLE);
    }

    //Place la reine à la position donné
    public void createColony(Integer row,Integer column){
        Noeud n = getNoeud(row , column);
        //n.setNoeudState(Noeud.STATE.ANTHILL);
        Reine r = new Reine(n);
    }

}
