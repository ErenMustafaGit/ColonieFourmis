package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.BitSet;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioTest {

    static final int WIDTH = 3;
    static final int HEIGHT = 3;
    static final int FOODQUANTITY = 6000;
    static final int FOODPARAM = 1;
    static final int EVAPORATIONPARAM = 0;
    static final int PHEROMONEPARAM = 0;
    static final int MARGE_ERROR = 100;
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


        appli.setParameters(EVAPORATIONPARAM, FOODPARAM, PHEROMONEPARAM);
    }

    @Test
    @DisplayName("Test personnel : Fourmi se déplace obligatoirement vers le Noeud avec le plus de nourriture")
    void testNourriture() throws IOException{
        appli.putFood(2,1,10);
        appli.putFood(1,2, 1);
        appli.createWorkers(1);


        //Fourmis au milieu
        BitSet[][] bitSets = appli.play(1,true);
        assertTrue(bitSets[1][1].get(3));

        //Fourmis sur la nourriture
        bitSets = appli.play(1,false);
        assertTrue(bitSets[2][1].get(4));
    }

    @Test
    @DisplayName("Scenario de test 2 : a et b")
    void testa() throws IOException {
        appli.createWorkers(1);

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

        //Récupération du graphe de l'appli
        Graph graph = ((ControlAnt)this.appli).getGraph();

        //Récupération des 5 noeuds nécessaires
        Node centerNode = graph.getNode(1,1);
        Node topNode = graph.getNode(0,1);
        Node bottomNode = graph.getNode(2,1);
        Node rightNode = graph.getNode(1,2);
        Node antHillNode = graph.getNode(1,0);


        int topCount = 0;
        int bottomCount = 0;
        int rightCount = 0;

        //Boucle effectué 6000 fois pour la probabilité
        for(int i = 0; i<6000; i++){

            //Instanciation d'un ouvrier dans le noeud central
            Worker worker = new Worker(centerNode, null);

            //Ajout du noeud de gauche à son historique de noeud, afin qu'il n'y aille pas car il est censé venir de la bas
            worker.addToRecordsPath(antHillNode);

            //Déplacement de l'ouvrier
            worker.move();

            //Enregistrement de sa position dans un des 3 compteurs
            if(worker.getPosition() == topNode)
                topCount++;
            else if(worker.getPosition() == bottomNode)
                bottomCount++;
            else if(worker.getPosition() == rightNode)
                rightCount++;
        }

        //Print de debug
        System.out.println("Top : " + topCount + "\nBottom : " + bottomCount + "\nRight : " + rightCount);
        boolean topCheck = false;
        boolean bottomCheck = false;
        boolean rightCheck = false;

        //Verification si les probabilités sont juste (1/2, 1/3, 1/6)
        //En utilisant une intervalle avec marge d'erreur
        if((topCount <= (6000/2) + MARGE_ERROR) && (topCount >= (6000/2) - MARGE_ERROR))
            topCheck = true;
        if((bottomCount <= (6000/6) + MARGE_ERROR) && (bottomCount >= (6000/6) - MARGE_ERROR))
            bottomCheck = true;
        if((rightCount <= (6000/3) + MARGE_ERROR) && (rightCount >= (6000/3) - MARGE_ERROR))
            rightCheck = true;

        assertTrue(topCheck);
        assertTrue(bottomCheck);
        assertTrue(rightCheck);
    }

}
