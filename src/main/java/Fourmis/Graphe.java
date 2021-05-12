package Fourmis;

import java.util.ArrayList;
import java.util.List;

public class Graphe {
    private int width;
    private int height;
    private List<Noeud> graphe_tab;

    public Graphe(Integer width, Integer height){
        graphe_tab = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Noeud n = new Noeud();
                graphe_tab.add(n);
            }
        }
    }

    public void instanciateLink(){
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

                //Ajout de noeud voisins (celui du bas et droite)
                n.getVoisins().add(nRight);
                n.getVoisins().add(nDown);

                //Ajout de noeud voisins appartenant à celui de droite et du bas, le noeud actuel
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

            //Ajout de noeud voisins (celui du bas et droite)
            n.getVoisins().add(nDown);

            //Ajout de noeud voisins appartenant à celui du bas, le noeud actuel
            nDown.getVoisins().add(n);
        }

        //Noeud de tout en bas
        for(int y = 0; y<width-1; y++){
            //Récuperation Noeud actuel
            Noeud n = this.getNoeud(height-1,y);

             //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
            Noeud nRight = this.getNoeud(height-1,y+1);

            //Ajout de noeud voisins (celui de droite)
            n.getVoisins().add(nRight);

            //Ajout de noeud voisins appartenant à celui de droite , le noeud actuel
            nRight.getVoisins().add(n);
        }
    }



    public Noeud getNoeud(Integer row, Integer column){
        return graphe_tab.get(row + column * width);
    }

    public void putObstacle(Integer row, Integer column){
        Noeud n = graphe_tab.get(row + column * width);
        n.setFree(false);
    }
}
