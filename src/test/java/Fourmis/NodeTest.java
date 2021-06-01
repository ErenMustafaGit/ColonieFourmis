package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
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
        Node.STATE etat_colonnie = g.getNoeud(2,2).getNodeState();
        Node.STATE etat_obstacle = g.getNoeud(0,1).getNodeState();
        Node.STATE etat_free = g.getNoeud(0,10).getNodeState();
        assertEquals(Node.STATE.ANTHILL, etat_colonnie);
        assertEquals(Node.STATE.OBSTACLE, etat_obstacle);
        assertEquals(Node.STATE.FREE, etat_free);
    }

    @Test
    @DisplayName(("Changement d'état d'un du noeud"))
    void testSetStateNoeud(){
        g.getNoeud(4,4).setNodeState(Node.STATE.ANTHILL);
        Node.STATE etat = Node.STATE.ANTHILL;
        assertEquals(Node.STATE.ANTHILL, etat);
    }

    @Test
    @DisplayName(("Ajout d'un noeud voisin à un noeud"))
    void testAddNoeudVoisin(){
        Node n = new Node();
        Node n2 = new Node();
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
        Node n = g.getNoeud(0,0);
        int nb = n.getFreeVoisins().size();
        assertEquals(0, nb);

        //Noeud non encerclé
        Node n2 = g.getNoeud(4,4);
        int nb2 = n2.getFreeVoisins().size();
        assertEquals(4, nb2);

        //Noeud en corner
        Node nodeCorner = g.getNoeud(HEIGHT-1,WIDTH-1);
        int nbCorner = nodeCorner.getFreeVoisins().size();
        assertEquals(2, nbCorner);
    }

    @Test
    @DisplayName(("Voisins d'un noeud"))
    void testVoisinNoeud(){
        Node n = g.getNoeud(0,0);
        List<Node> voisins = n.getVoisins();
        Node n1 = g.getNoeud(0,1);
        Node n2 = g.getNoeud(1,0);

        assertTrue(voisins.contains(n1));
        assertTrue(voisins.contains(n2));
    }

    @Test
    @DisplayName("CompareTo/sort : Trie croissant des noeuds par rapport aux phéromones dessus ")
    void testCompareTo(){
        appli.putPheromone(1,2,40);
        appli.putPheromone(2,1, 10);
        appli.putPheromone(3,2, 20);

        Node position = appli.getGraph().getNoeud(2,2);
        List<Node> nodeVoisins = position.getFreeVoisins();

        for(Node node : nodeVoisins){
            int pheromoneQuantity = 0;
            for(Pheromone pheromone : node.getPheromone()){
                pheromoneQuantity += pheromone.getQuantity();
            }
            System.out.println(node + "| ph : " + pheromoneQuantity);
        }

        Collections.shuffle(nodeVoisins);
        Collections.sort(nodeVoisins);

        System.out.println("\n\nTRIE !");
        for(Node node : nodeVoisins){
            int pheromoneQuantity = 0;
            for(Pheromone pheromone : node.getPheromone()){
                pheromoneQuantity += pheromone.getQuantity();
            }
            System.out.println(node + "| ph : " + pheromoneQuantity);
        }

        //Ordre attendue :
        assertEquals(appli.getGraph().getNoeud(2,3),nodeVoisins.get(0) ); //0 de pheromone
        assertEquals(appli.getGraph().getNoeud(2,1),nodeVoisins.get(1) ); //10 de pheromone
        assertEquals(appli.getGraph().getNoeud(3,2),nodeVoisins.get(2) ); //20 de pheromone
        assertEquals(appli.getGraph().getNoeud(1,2),nodeVoisins.get(3) ); //40 de pheromone

    }



}