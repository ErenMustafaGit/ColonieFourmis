package Fourmis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitaireReine {
    private int HEIGHT = 5;
    private int WIDTH = 5;

    AntFacadeController control;

    @BeforeEach
    void setup(){
        control = new ControlFourmis();
        control.createGrid(WIDTH, HEIGHT);
        control.createColony(1,2);
    }

    @Test
    @DisplayName(("Creation de la fourmilière"))
    void testReine(){
        BitSet[][] bitsets = control.play(0, false);
        BitSet actuel = bitsets[1][2];
        assertTrue(actuel.get(0));
    }

    @Test
    @DisplayName(("Creation de soldat"))
    void testCreationSoldat(){
        control.createSoldiers(5);
        BitSet[][] bitsets = control.play(0, false);
        BitSet actuel = bitsets[1][2];
        assertTrue(actuel.get(2));
    }

}
