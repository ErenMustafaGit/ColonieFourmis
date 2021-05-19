package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrapheTest {
    Graphe graphe;
    public int WIDTH = 2;
    public int HEIGHT = 4;

    @BeforeEach
    void setUp() {

        graphe = new Graphe(WIDTH,HEIGHT);
    }

    @Test
    @DisplayName("Récuperer un noeud pas dans le graphe")
    void getNoeud() {
        assertThrows(IndexOutOfBoundsException.class, ()->{
            graphe.getNoeud(WIDTH, HEIGHT);
        });

        assertThrows(IndexOutOfBoundsException.class, ()->{
            graphe.getNoeud(-1, -1);
        });
    }

    @Test
    void putObstacle() {
        graphe.putObstacle(2,1);
        Noeud actual = graphe.getNoeud(2,1);
        assertEquals(Noeud.STATE.OBSTACLE, actual.getNoeudState());

        assertThrows(IndexOutOfBoundsException.class, ()->{
            graphe.putObstacle(WIDTH, HEIGHT);
        });
    }

    @Test
    void createColony() {
        graphe.createColony(1,2);
        Noeud actual = graphe.getNoeud(1,2);
        assertEquals(Noeud.STATE.ANTHILL, actual.getNoeudState());

        assertThrows(IndexOutOfBoundsException.class, ()->{
            graphe.createColony(WIDTH, HEIGHT);
        });
    }

    @Test
    @DisplayName("NoeudList contient tout les noeuds du Graphe")
    void getNoeudList() {
        List<Noeud> noeudList = graphe.getNoeudList();

        for(int i = 0; i<WIDTH;i++){
            for(int j = 0; j<HEIGHT;j++){
                assertTrue(noeudList.contains(graphe.getNoeud(i,j)));
            }
        }

    }

    @Test
    @DisplayName(("Longueur du graphe"))
    void getWidth() {
        assertEquals(WIDTH, graphe.getWidth());
    }

    @Test
    @DisplayName(("Hauteur du graphe"))
    void getHeight() {
        assertEquals(HEIGHT, graphe.getHeight());
    }


    @Test
    @DisplayName("Nombre de noeud dans le Graphe")
    void nbNoeud()
    {
        List<Noeud> noeudList = graphe.getNoeudList();
        assertEquals(WIDTH*HEIGHT, noeudList.size());
    }

}