package Fourmis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {

    @Test
    @DisplayName(("mouvement soldat"))
    void move() {
        Graph g = new Graph(5,5);
        g.putObstacle(1,0);
        g.putObstacle(1,1);
        g.putObstacle(1,2);
        Soldier s = new Soldier(g.getNoeud(0,0));
        s.move();
        Node n = g.getNoeud(0, 1);
        assertEquals(n, s.getPosition());
    }
}