package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void createWorkers() {
    }

    @Test
    void play() {
    }
}