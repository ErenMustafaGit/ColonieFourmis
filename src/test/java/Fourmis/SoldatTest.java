package Fourmis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class SoldatTest {

    @Test
    @DisplayName(("mouvement soldat"))
    void move() {
        Graphe g = new Graphe(5,5);
        g.putObstacle(1,0);
        g.putObstacle(1,1);
        g.putObstacle(1,2);
        Soldat s = new Soldat(g.getNoeud(0,0));
        s.move();
        Noeud n = g.getNoeud(0, 1);
        assertEquals(n, s.getPosition());
    }
}