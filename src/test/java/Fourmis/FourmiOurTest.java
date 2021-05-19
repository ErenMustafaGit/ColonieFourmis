package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FourmiOurTest {
    AntFacadeController appli;
    static final int WIDTH = 13;
    static final int HEIGHT = 19;


    @BeforeEach
    void setUp()
    {
        appli = new ControlFourmis();
        appli.createGrid(WIDTH, HEIGHT);
        appli.createColony(2,2);

    }

    /**
     * Test Unitaire de la classe Soldat
     */

    @Test
    @DisplayName(("Soldat qui se déplacer alors qu'il est entouré d'obstacle"))
    void testMove()
    {
        appli.putObstacle(0,1);
        appli.putObstacle(1,0);
        appli.putObstacle(2,1);
        appli.putObstacle(1,2);
        try{
            appli.createColony(1,1);
            appli.createSoldiers(2);
            BitSet[][] bitset = appli.play(1,false);
            BitSet actual = bitset[1][1];
            assertTrue(actual.get(0), "Fourmillière bien présente sur le noeud placé " + actual);
            assertTrue(actual.get(2), "Soldat bien présent sur le noeud placé, ils ne se sont donc pas déplacé " + actual);
        }catch (IllegalArgumentException exception)
        {
            // OK
        }
        catch (Exception exception)
        {
            fail("Exception de mauvais type : "
                    + exception.getClass().getSimpleName());
        }
    }


}
