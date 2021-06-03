package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {
    ControlAnt appli;
    static final int WIDTH = 13;
    static final int HEIGHT = 19;
    Graph g;

    @BeforeEach
    void setUp()
    {
        appli = new ControlAnt();
        appli.createGrid(WIDTH, HEIGHT);
        g = appli.getGraph();
        appli.createColony(2,2);
        g.putObstacle(0,1);
        g.putObstacle(1,0);
    }

    /*
    @Test
    @DisplayName(("mouvement soldat"))
    void move() {
        Node pos_col= g.getNoeud(0,0);
        Queen colony = new Queen(pos_col);
        //appli.putObstacle(1,0);
        //appli.putObstacle(1,1);
        //appli.putObstacle(1,2);
        Soldier s = new Soldier(g.getNoeud(0,0), colony);
        s.move();
        Node n = g.getNoeud(0, 1);
        assertEquals(n, s.getPosition());
    }*/

    @Test
    @DisplayName(("mouvement soldat bloqué"))
    void moveBlocked() {
        Graph g = new Graph(5,5);
        g.putObstacle(1,0);
        g.putObstacle(1,1);
        g.putObstacle(0,1);
        Node position = g.getNode(0,0);


        //Soldier s = new Soldier(g.getNoeud(0,0));
        //s.move();
        //assertEquals(position, s.getPosition());
    }

}