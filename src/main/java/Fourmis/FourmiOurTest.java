package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.fail;

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

    @Test
    @DisplayName(("Placer 4 obstacles sur la fourmilière"))
    void testPutObstacle()
    {
        try{
            appli.putObstacle(2,1);
            appli.putObstacle(2,2);
            appli.putObstacle(3,2);
            appli.putObstacle(2,3);
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
