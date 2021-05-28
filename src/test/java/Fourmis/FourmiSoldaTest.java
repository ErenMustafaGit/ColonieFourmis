package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class FourmiSoldierTest
{
    static final int WIDTH = 13;
    static final int HEIGHT = 19;

    AntFacadeController appli;


    @BeforeEach
    void setUp()
    {
        appli = new ControlAnt();
        appli.createGrid(WIDTH, HEIGHT);
        appli.createColony(0,0);

        for (int i = 1; i < HEIGHT; i += 2)
        {
            int decalage = ((i - 1) % 4) / 2;
            for (int j = decalage; j < WIDTH - 1 + decalage; j++)
            {
                appli.putObstacle(i,j);
            }
        }
        appli.createSoldiers(1);
    }

    @Test
    @DisplayName(("Placer un obstacle sur la fourmilière"))
    void testPutObstacle()
    {
        try
        {
            appli.putObstacle(0, 0);
            fail("Obstacle placé sur la fourmilière");
        }
        catch (IllegalArgumentException exception)
        {
            // OK
        }
        catch (Exception exception)
        {
            fail("Exception de mauvais type : "
                    + exception.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Fourmilière correctement initialisée")
    void test0()
    {
        BitSet[][] bitsets = appli.play(0, false);
        BitSet actual = bitsets[0][0];
        assertTrue(actual.get(0), "Fourmilière absente : bitsets[0][0] = " + actual);
        assertFalse(actual.get(1), "Obstacle sur la fourmilière : bitsets[0][0] = " + actual);
        assertTrue(actual.get(2), "Soldat absent bitsets[0][0] = " + actual);
        BitSet expected = new BitSet();
        expected.set(0);
        expected.set(2);
        assertEquals(expected, actual,
                "Foumilière : bitsets[0][0] = " + actual);

    }

    @Test
    @DisplayName("Obstacles correctement placés")
    void test1()
    {
        BitSet[][] bitsets = appli.play(0, false);

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
            {
                BitSet bitset = bitsets[i][j];
                boolean actual = bitset.get(1);
                boolean expected =
                        (i % 4 == 1 && j != WIDTH - 1)
                                || (i % 4 == 3 && j != 0);
                assertEquals(expected, actual,
                        "Obstacle mal placé : bitsets[" + i + "][" + j + "] = " + bitset);
            }
    }

    @Test
    @DisplayName("Déplacement élémentaire")
    void test2()
    {
        //Déplacement élémentaire dans un couloir
        BitSet[][] bitsets = appli.play(1, false);
        BitSet actual = bitsets[0][1];
        boolean soldatPresent = actual.get(2);
        assertTrue(soldatPresent,
                "Soldat absent : bitsets[0][1] = " + actual);
    }

    @Test
    @DisplayName("Fourmi bloquée")
    void test3()
    {
        //Déplacement élémentaire bloqué (d)
        appli.putObstacle(0, 1);
        BitSet[][] bitsets = appli.play(1, false);
        BitSet actual = bitsets[0][0];
        boolean soldatPresent = actual.get(2);
        assertTrue(soldatPresent,
                "Soldat absent : bitsets[0][0] = " + actual);
    }

    @Test
    @DisplayName("Fourmi dans le couloir")
    void test4()
    {
        BitSet[][] bitsets;

        boolean surFourmiliere;
        do
        {
            bitsets = appli.play(2, false);
            surFourmiliere= bitsets[0][0].get(2);
        }
        while (surFourmiliere);

        appli.putObstacle(0, 1);
        int i = 0;
        int j = 2;
        while (i < HEIGHT)
        {
            BitSet actual = bitsets[i][j];
            boolean soldatPresent = actual.get(2);
            assertTrue(soldatPresent,
                    "bitsets[" + i + "][" + j + "] = " + bitsets[i][j]);
            bitsets = appli.play(1, false);
            appli.putObstacle(i, j);

            if (i % 4 == 0 && j < WIDTH - 1)
                j++;
            else if (i % 4 == 2 && j > 0)
                j--;
            else
                i++;
        }
    }

    @Test
    @DisplayName("Nourriture impossible à placé sur fourmillière/obstacle")
    void test5()
    {
        //Nourriture sur une fourmillière
        assertThrows(IllegalArgumentException.class, ()->{
            appli.putFood(0,0,1);
        });

        //Nourriture sur un obstacle
        assertThrows(IllegalArgumentException.class, ()->{
            appli.putFood(1,0,1);
        });

        //Obstacle sur de la nourriture
        appli.putFood(0,3,1);
        assertThrows(IllegalArgumentException.class, ()->{
            appli.putObstacle(0,3);
        });
    }
    @Test
    @DisplayName("Trajet aller-retour")
    void test6()
    {
        appli.createWorkers(1);
        appli.putFood(0,WIDTH-1, 100);

        //Trajet aller pour aller chercher la nourriture
        for(int i = 1; i<=WIDTH-1; i++){
            //TOUR 1
            BitSet[][] bitSets = appli.play(1, false);
            BitSet bitset = bitSets[0][i];
            //Si la fourmi soldat a bien avancé
            assertTrue(bitset.get(2));

            //Si il y a bien une ouvrière sans nourriture
            System.out.println(i);
            assertTrue(bitset.get(3));
        }

        for(int i = WIDTH-1; i<=0; i++){
            //TOUR 1
            BitSet[][] bitSets = appli.play(1, false);
            BitSet bitset = bitSets[0][i];

            //Si il y a bien une ouvrière avec nourriture
            System.out.println(i);
            assertTrue(bitset.get(4));
        }


    }
    @Test
    @DisplayName("Fourmi bloquée")
    void test7()
    {

    }
    @Test
    @DisplayName("Fourmi bloquée")
    void test8()
    {

    }
    @Test
    @DisplayName("Fourmi bloquée")
    void test9()
    {

    }
}