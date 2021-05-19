package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrapheTest {
    Graphe graphe;
    public int width;
    public int height;

    @BeforeEach
    void setUp() {
        int width = 2;
        int height = 4;
        graphe = new Graphe(width,height);
    }

    @Test
    void getNoeud() {
    }

    @Test
    void putObstacle() {
    }

    @Test
    void createColony() {
    }

    @Test
    void getNoeudList() {
    }

    @Test
    void getWidth() {
    }

    @Test
    void getHeight() {
    }


    @Test
    @DisplayName(("Test du nombre de noeud dans le Graphe"))
    void testNbNoeud()
    {

        List<Noeud> noeudList = graphe.getNoeudList();
        assertEquals(width*height, noeudList.size());
    }

    @Test
    @DisplayName(("Placer un obstacle sur un noeud et le récuperer avec la class Graphe"))
    void testPutObstacleGetNoeud()
    {
        graphe.putObstacle(1,2);
        Noeud actual = graphe.getNoeud(1,2);
        assertEquals(Noeud.STATE.OBSTACLE, actual.getNoeudState());
    }

    @Test
    @DisplayName(("Placer un obstacle sur un noeud et le récuperer avec la class Graphe"))
    void testCreateColonyGetNoeud()
    {
        graphe.createColony(1,2);
        Noeud actual = graphe.getNoeud(1,2);
        assertEquals(Noeud.STATE.ANTHILL, actual.getNoeudState());
    }
}