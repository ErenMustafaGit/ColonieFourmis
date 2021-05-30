package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScenarioTest {

    static final int WIDTH = 3;
    static final int HEIGHT = 3;
    static final int FOODPARAM = 20;
    static final int EVAPORATIONPARAM = 1;
    static final int PHEROMONEPARAM = WIDTH*EVAPORATIONPARAM;
    AntFacadeController appli;

    @BeforeEach
    void setUp()
    {
        appli = new ControlAnt();
        appli.createGrid(WIDTH, HEIGHT);
        appli.createColony(0,0);


        appli.createSoldiers(1);
        appli.createWorkers(1);

        appli.setParameters(EVAPORATIONPARAM, FOODPARAM, PHEROMONEPARAM);
    }

    @Test
    @DisplayName("Scenario de test 2")
    void test1(){

    }
}
