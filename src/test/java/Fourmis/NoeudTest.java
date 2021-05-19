package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoeudTest {
    AntFacadeController appli;
    static final int WIDTH = 13;
    static final int HEIGHT = 19;
    Graphe g;


    @BeforeEach
    void setUp()
    {
        /*
        appli = new ControlFourmis();
        appli.createGrid(WIDTH, HEIGHT);
        appli.createColony(2,2);
         */
        g = new Graphe(WIDTH, HEIGHT);
        g.createColony(2,2);
        g.putObstacle(0,1);
        g.putObstacle(1,0);

    }


    /**
     * Test Unitaire de la classe Noeud
     */
    @Test
    @DisplayName(("Etat du noeud"))
    void testStateNoeud(){
        String etat_colonnie = g.getNoeud(2,2).getNoeudState().toString();
        String etat_obstacle = g.getNoeud(0,1).getNoeudState().toString();
        assertEquals("ANTHILL", etat_colonnie);
        assertEquals("OBSTACLE", etat_obstacle);
    }

    @Test
    @DisplayName(("Voisin disponible d'un noeud"))
    void testVoinsinNoeud(){
        //Pas de voisin car le noeud en 0,0 est encerclé
        Noeud n = g.getNoeud(0,0);
        int nb = n.getFreeVoisins().size();
        assertEquals(0, nb);

        //Noeud non encerclé
        Noeud n2 = g.getNoeud(4,4);
        int nb2 = n2.getFreeVoisins().size();
        assertEquals(4, nb2);
    }

}