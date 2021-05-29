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
        appli.setParameters(0, 20, 20);

        //Trajet aller pour aller chercher la nourriture
        for(int i = 1; i<=WIDTH-1; i++){

            BitSet[][] bitSets = appli.play(1, false);

            BitSet bitset = bitSets[0][i];
            //Si la fourmi soldat a bien avancé
            //assertTrue(bitset.get(2));

            System.out.print(i +" : ");


            //Les noeuds entre la colonie et la nourriture
            if(i != WIDTH - 1) {
                //Verification si il y a bien une ouvrière sans nourriture
                assertTrue(bitset.get(3));

                //Vérification si il n'y a pas de phéromone laché
                assertFalse(bitset.get(6));
            }

            //Noeud contenant la nourriture
            else if(i == WIDTH - 1){

                //Verification si il y a bien une ouvrière avec de la nourriture
                assertTrue(bitset.get(4));
                System.out.println("nourriture sur fourmis");
            }


        }

        //Trajet retour
        for(int i = WIDTH-1; i>=0; i--){

            BitSet[][] bitSets = appli.play(1, false);

            //Noeud où l'ouvrière est censé se trouver si elle poursuit correctement son chemin dans le couloir
            BitSet bitset = bitSets[0][i];
            System.out.print(i +" : ");

            //Si il y a bien une ouvrière avec nourriture
            assertTrue(bitset.get(4));

            //Noeud de fin (censé contenir la nourriture) donc ne contient pas de phéromone
            if(i == WIDTH - 1){
                //Verification si le noeud ne contient pas de phéromone
                assertFalse(bitset.get(6));
                System.out.println("pas de phéromone");
            }

            //Les noeuds entre la colonie et la nourriture
            else if(i != 0){
                //Case contenant de la phéromone
                assertTrue(bitset.get(6));
                System.out.println("phéromone présente ");
            }

            //Si nous sommes sur la case de départ (colonie)
            else{

                //Retour dans la colonnie
                assertTrue(bitset.get(0));
                System.out.println("colonnie");

                //Vérification qu'il n'y ai pas de phéromone dans la colonnie
                assertFalse(bitset.get(6));
            }
        }


    }
    @Test
    @DisplayName("Si la nouriture est bien placé")
    void test7()
    {
        appli.putFood(0,8, 7);
        BitSet[][] bitSets = appli.play(1, false);
        BitSet bitset = bitSets[0][8];
        assertTrue(bitset.get(5));
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