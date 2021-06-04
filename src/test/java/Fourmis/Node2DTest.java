package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Node2DTest {
    ControlAnt appli;
    static final int WIDTH = 13;
    static final int HEIGHT = 19;
    Graph g;



    @BeforeEach
    void setUp()
    {
        appli = new ControlAnt();
        appli.createGrid(WIDTH, HEIGHT);
        g = appli.getGraph();
        appli.createColony(2,2);
        g.putObstacle(0,1);
        g.putObstacle(1,0);
    }


    /**
     * Test Unitaire de la classe Noeud
     */
    @Test
    @DisplayName(("Etat du noeud"))
    void testStateNoeud(){
        State etat_colonnie = g.getNode(2,2).getNodeState();
        State etat_obstacle = g.getNode(0,1).getNodeState();
        State etat_free = g.getNode(0,10).getNodeState();
        assertEquals(State.ANTHILL, etat_colonnie);
        assertEquals(State.OBSTACLE, etat_obstacle);
        assertEquals(State.FREE, etat_free);
    }

    @Test
    @DisplayName(("Changement d'état d'un du noeud"))
    void testSetStateNoeud(){
        g.getNode(4,4).setNodeState(State.ANTHILL);
        State etat = State.ANTHILL;
        assertEquals(State.ANTHILL, etat);
    }

    @Test
    @DisplayName(("Ajout d'un noeud voisin à un noeud"))
    void testAddNoeudVoisin(){
        Node2D n = new Node2D();
        Node2D n2 = new Node2D();
        n.addNoeudVoisin(n2);
        n2.addNoeudVoisin(n);

        List<Node> nvoisins = n.getVoisins();
        List<Node> n2voisins = n2.getVoisins();

        assertTrue(nvoisins.contains(n2));
        assertTrue(n2voisins.contains(n));

    }

    @Test
    @DisplayName(("Voisins disponible d'un noeud"))
    void testFreeVoinsinNoeud(){
        //Pas de voisin car le noeud en 0,0 est encerclé
        Node2D n = g.getNode(0,0);
        int nb = n.getFreeVoisins().size();
        assertEquals(0, nb);

        //Noeud non encerclé
        Node2D n2 = g.getNode(4,4);
        int nb2 = n2.getFreeVoisins().size();
        assertEquals(4, nb2);

        //Noeud en corner
        Node2D node2DCorner = g.getNode(HEIGHT-1,WIDTH-1);
        int nbCorner = node2DCorner.getFreeVoisins().size();
        assertEquals(2, nbCorner);
    }

    @Test
    @DisplayName(("Voisins d'un noeud"))
    void testVoisinNoeud(){
        Node n = g.getNode(0,0);
        List<Node> voisins = n.getVoisins();
        Node n1 = g.getNode(0,1);
        Node n2 = g.getNode(1,0);

        assertTrue(voisins.contains(n1));
        assertTrue(voisins.contains(n2));
    }

    @Test
    @DisplayName("CompareTo/sort : Trie croissant des noeuds par rapport aux phéromones dessus ")
    void testCompareTo(){
        appli.putPheromone(1,2,40);
        appli.putPheromone(2,1, 10);
        appli.putPheromone(3,2, 20);

        Node2D position = appli.getGraph().getNode(2,2);
        List<Node> node2DVoisins = position.getFreeVoisins();

        for(Node node2D : node2DVoisins){
            int pheromoneQuantity = 0;
            for(Pheromone pheromone : node2D.getPheromone()){
                pheromoneQuantity += pheromone.getQuantity();
            }
            System.out.println(node2D + "| ph : " + pheromoneQuantity);
        }

        Collections.shuffle(node2DVoisins);
        Collections.sort(node2DVoisins);

        System.out.println("\n\nTRIE !");
        for(Node node2D : node2DVoisins){
            int pheromoneQuantity = 0;
            for(Pheromone pheromone : node2D.getPheromone()){
                pheromoneQuantity += pheromone.getQuantity();
            }
            System.out.println(node2D + "| ph : " + pheromoneQuantity);
        }

        //Ordre attendue :
        assertEquals(appli.getGraph().getNode(2,3), node2DVoisins.get(0) ); //0 de pheromone
        assertEquals(appli.getGraph().getNode(2,1), node2DVoisins.get(1) ); //10 de pheromone
        assertEquals(appli.getGraph().getNode(3,2), node2DVoisins.get(2) ); //20 de pheromone
        assertEquals(appli.getGraph().getNode(1,2), node2DVoisins.get(3) ); //40 de pheromone

    }



}