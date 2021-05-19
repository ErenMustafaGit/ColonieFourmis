package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class ReineTest {
    AntFacadeController appli;
    static final int WIDTH = 3;
    static final int HEIGHT = 4;
    Graphe graphe;

    @BeforeEach
    void setUp() {
        appli = new ControlFourmis();
        appli.createGrid(WIDTH, HEIGHT);
        graphe = new Graphe(WIDTH, HEIGHT);
    }

    @Test
    @DisplayName(("Reine qui ne doit pas se déplacer"))
    void move() {
        Reine reine = new Reine(graphe.getNoeud(0,0));
        reine.move();
        assertEquals(graphe.getNoeud(0,0), reine.getPosition());
    }

    @Test
    void createSoldiers() {


        appli.createColony(1,2);
        appli.createSoldiers(5);
        BitSet[][] bitset = appli.play(0,false);
        BitSet actual = bitset[1][2];
        assertTrue(actual.get(2), "Il y a des soldats : " + actual);
    }

    @Test
    @DisplayName("Les soldats qu'une colonie crée, bouge bien")
    void createSoldiersMove() {

        appli.createColony(0,0);
        appli.putObstacle(1,0);

        appli.createSoldiers(1);
        BitSet[][] bitset = appli.play(1,false);
        BitSet right = bitset[0][1];
        BitSet down = bitset[1][0];
        assertTrue(right.get(2), "Soldat a bouger : " + right);
        assertFalse(down.get(2), "Soldat n'a pas bouger dans l'obstacle : " + down);

    }
}