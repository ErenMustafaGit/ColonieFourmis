package Fourmis;

import java.util.List;

public class Graphe {
    private Integer width;
    private Integer height;
    private Noeud[][] graphe_tab;

    public Graphe(Integer width, Integer height){
        graphe_tab = new Noeud[width][height];
        for(int i = 0; i < graphe_tab.length; i++){
            for(int j = 0; j < graphe_tab[i].length; j++){
                Noeud n = new Noeud();
                graphe_tab[i][j] = n;
            }
        }
    }

    public Noeud getNoeud(Integer row, Integer column){
        return graphe_tab[row][column];
    }

    public void putObstacle(Integer row, Integer column){
        Noeud n = graphe_tab[row][column];
        n.setFree(false);
    }
}
