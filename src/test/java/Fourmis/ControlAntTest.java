package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class ControlAntTest {
    ControlAnt appli;
    static final int WIDTH = 13;
    static final int HEIGHT = 19;
    Graph graph;

    @BeforeEach
    void setUp() {
        appli = new ControlAnt();
        appli.createGrid(WIDTH, HEIGHT);
        graph = appli.getGraph();
    }

    @Test
    void createGrid() {
        appli.createGrid(WIDTH, HEIGHT);
        assertTrue(appli.getGraph() != null);
    }
    @Test
    void getGraph(){
        graph = appli.getGraph();
        assertTrue(graph instanceof Graph);
    }

    @Test
    void getListeFourmis(){
        assertTrue(appli.getListeFourmis().isEmpty());
        appli.createSoldiers(4);
        assertTrue(appli.getListeFourmis().isEmpty());

        appli.createColony(0,0);
        assertEquals(1,appli.getListeFourmis().size());

        appli.createSoldiers(0);
        assertEquals(1,appli.getListeFourmis().size());
        appli.createSoldiers(10);
        assertEquals(11,appli.getListeFourmis().size());
    }

    @Test
    void putObstacle() {
        /*DEBUG POUR VOIR LE STATUT DE TOUT LES NOEUDS
        for(int i = 0; i<WIDTH; i++){
            for(int j = 0; j<HEIGHT; j++){
                System.out.println("Graph : " + i + "  " + j  + " = " + graph.getNoeud(i,j).getNodeState());
            }
        }*/
        appli.putObstacle(0,0);
        assertEquals(Node.STATE.OBSTACLE, graph.getNoeud(0,0).getNodeState());

    }

    @Test
    void createColony() {
        appli.createColony(0,0);
        assertEquals(Node.STATE.ANTHILL, graph.getNoeud(0,0).getNodeState());
    }

    @Test
    void createSoldiers() {
        //Aucune fourmis crée
        assertEquals(0, appli.getListeFourmis().size());
        appli.createSoldiers(10);
        assertEquals(0, appli.getListeFourmis().size());

        //22 = 2 Reine + 10 Soldat par Reine (20)
        appli.createColony(0,0);
        appli.createColony(1,1);
        appli.createSoldiers(10);
        assertEquals(22, appli.getListeFourmis().size());
    }


    @Test
    void play() {
        appli.createColony(0,0);
        appli.putObstacle(1,0);
        appli.putObstacle(2,0);
        appli.putObstacle(3,0);
        appli.putObstacle(4,0);
        appli.createSoldiers(5);
        BitSet[][] bitsets = appli.play(1, false);
        //Si la fourmillière est bien reconnu
        assertTrue(bitsets[0][0].get(0));

        //Si les soldats se sont bien déplacé de 1 et sont bien reconnu
        assertTrue(bitsets[0][1].get(2));

        //Si les obstacles sont bien reconnus
        assertTrue(bitsets[1][0].get(1));
        assertTrue(bitsets[2][0].get(1));
        assertTrue(bitsets[3][0].get(1));
        assertTrue(bitsets[4][0].get(1));

        //Verification si il ne les confonds pas
        assertFalse(bitsets[0][0].get(2));
        assertFalse(bitsets[0][0].get(1));
        assertFalse(bitsets[1][0].get(0));
        assertFalse(bitsets[2][0].get(0));
        assertFalse(bitsets[3][0].get(0));
        assertFalse(bitsets[4][0].get(0));
        assertFalse(bitsets[0][1].get(1));
        assertFalse(bitsets[0][1].get(0));
    }
}