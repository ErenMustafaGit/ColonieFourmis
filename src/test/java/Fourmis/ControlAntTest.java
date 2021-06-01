package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
        assertTrue(appli.getAntList().isEmpty());
        appli.createSoldiers(4);
        assertTrue(appli.getAntList().isEmpty());

        appli.createColony(0,0);
        assertEquals(1,appli.getAntList().size());

        appli.createSoldiers(0);
        assertEquals(1,appli.getAntList().size());
        appli.createSoldiers(10);
        assertEquals(11,appli.getAntList().size());
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
        appli.createColony(1,2);
        Node actual = graph.getNoeud(1,2);
        assertEquals(Node.STATE.ANTHILL, actual.getNodeState());

        assertThrows(IndexOutOfBoundsException.class, ()->{
            appli.createColony(HEIGHT, WIDTH);
        });
    }

    @Test
    void createSoldiers() {
        //Création de soldat sans reine
        appli.createSoldiers(10);
        assertEquals(0, appli.getAntList().size());

        //Création de soldat avec 2 reines
        appli.createColony(0,0);
        appli.createColony(1,1);
        appli.createSoldiers(10);
        //22 = 2 Reine + 20 Soldats
        assertEquals(22, appli.getAntList().size());
    }

    @Test
    void createWorkers() {
        //Création d'ouvrier sans reine
        appli.createWorkers(10);
        assertEquals(0, appli.getAntList().size());

        //Création d'ouvrer avec 2 reines
        appli.createColony(0,0);
        appli.createColony(1,1);
        appli.createWorkers(10);
        //22 = 2 Reine + 20 Soldats
        assertEquals(22, appli.getAntList().size());
    }


    @Test
    void play() throws IOException {
        BitSet[][] bitset = appli.play(5, true);
        BitSet actual;
        for(int i = 0; i<bitset.length; i++){
            for(int j = 0; j<bitset[0].length;j++){
                actual = bitset[i][j];
                for (int k = 0; k< actual.length(); k++){
                    assertFalse(actual.get(k));
                }
            }
        }



        //Appli rempli de fourmillière
        /*
        for(int i = 0; i<WIDTH; i++){
            for(int j = 0; j<HEIGHT;j++){
                appli.createColony(i,j);
            }
        }
        BitSet[][] bitset2 = appli.play(5, false);
        BitSet actual2;
        for(int i = 0; i<bitset2.length; i++){
            for(int j = 0; j<bitset2[0].length;j++){
                actual2 = bitset[i][j];
                assertTrue(actual2.get(0));
                for (int k = 1; k< actual2.length(); k++){
                    assertFalse(actual2.get(k));
                }
            }
        }
         */
    }
}