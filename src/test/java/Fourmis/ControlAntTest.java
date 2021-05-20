package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class ControlAntTest {
    Graph graph;
    public int WIDTH = 13;
    public int HEIGHT = 19;
    ControlAnt appli;

    @BeforeEach
    void setUp() {
        appli = new ControlAnt();
        appli.createGrid(WIDTH,HEIGHT);
        graph = appli.getGraph();
    }

    @Test
    void createGrid() {
    }

    @Test
    void putObstacle() {
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
        assertEquals(0, appli.getListeFourmis().size());

        //Création de soldat avec 2 reines
        appli.createColony(0,0);
        appli.createColony(1,1);
        appli.createSoldiers(10);
        //22 = 2 Reine + 20 Soldats
        assertEquals(22, appli.getListeFourmis().size());
    }

    @Test
    void createWorkers() {
        //Création d'ouvrier sans reine
        appli.createWorkers(10);
        assertEquals(0, appli.getListeFourmis().size());

        //Création d'ouvrer avec 2 reines
        appli.createColony(0,0);
        appli.createColony(1,1);
        appli.createWorkers(10);
        //22 = 2 Reine + 20 Soldats
        assertEquals(22, appli.getListeFourmis().size());
    }

    @Test
    void play() {
        BitSet[][] bitset = appli.play(5, false);
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