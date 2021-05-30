package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioTest {

    static final int WIDTH = 3;
    static final int HEIGHT = 3;
    static final int FOODQUANTITY = 6000;
    static final int FOODPARAM = 1;
    static final int EVAPORATIONPARAM = 0;
    static final int PHEROMONEPARAM = 0;
    AntFacadeController appli;

    @BeforeEach
    void setUp()
    {
        appli = new ControlAnt();

        appli.createGrid(WIDTH, HEIGHT);

        appli.putObstacle(0,0);
        appli.createColony(1,0);
        appli.putObstacle(2,0);

        ((ControlAnt)appli).putPheromone(0,1,2);
        ((ControlAnt)appli).putPheromone(1,2,1);

        appli.putFood(0,2, FOODQUANTITY);
        appli.putFood(2,2,FOODQUANTITY);


        appli.createWorkers(1);
        appli.setParameters(EVAPORATIONPARAM, FOODPARAM, PHEROMONEPARAM);
    }

    @Test
    @DisplayName("Scenario de test 2 : a et b")
    void testa(){

        //Situation initiale
        BitSet[][] bitSets = appli.play(0,false);

        //Noeud où il y a la fourmillière
        BitSet bitSet = bitSets[1][0];
        //Si il se trouve bien une fourmillière
        assertTrue(bitSet.get(0));
        //Et une ouvrière sans Nourriture
        assertTrue(bitSet.get(3));

        //Noeud où il y a les phéromones, Test si ils sont bien présent
        bitSet = bitSets[0][1];
        assertTrue(bitSet.get(6));
        bitSet = bitSets[1][2];
        assertTrue(bitSet.get(6));

        //Noeud où il y a les obstacles, Test si ils sont bien présent
        bitSet = bitSets[0][0];
        assertTrue(bitSet.get(1));
        bitSet = bitSets[2][0];
        assertTrue(bitSet.get(1));

        //Noeud où il y a les nourritures, Test si ils sont bien présent
        bitSet = bitSets[0][2];
        assertTrue(bitSet.get(5));
        bitSet = bitSets[2][2];
        assertTrue(bitSet.get(5));


        //Tour 1
        bitSets = appli.play(1,false);

        //a) Mouvement en 1,1 de l'ouvrière attendu
        bitSet = bitSets[1][1];
        assertTrue(bitSet.get(3));


        //Tour 2
        bitSets = appli.play(1,false);

        //b) 3 choix de mouvement : 0,1 | 1,2 | 2,1  de l'ouvrière attendu
        boolean goodMove = false;
        if(bitSets[0][1].get(3) || bitSets[1][2].get(3) ||bitSets[2][1].get(3))
            goodMove = true;
        assertTrue(goodMove);
    }

    @Test
    @DisplayName("Scénario de test : Probabilité avec 6000 essai")
    void testc(){

    }

}
