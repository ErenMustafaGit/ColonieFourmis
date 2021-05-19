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

    @BeforeEach
    void setUp() {
        appli = new ControlFourmis();
        appli.createGrid(WIDTH, HEIGHT);
    }

    @Test
    @DisplayName(("Reine qui ne doit pas se déplacer"))
    void move() {
        appli.createColony(1,2);
        BitSet[][] bitset = appli.play(5,false);
        BitSet actual = bitset[1][2];
        assertTrue(actual.get(0), "Fourmillière ne s'est pas déplacé " + actual);
    }

    @Test
    void createSoldiers() {



    }


}