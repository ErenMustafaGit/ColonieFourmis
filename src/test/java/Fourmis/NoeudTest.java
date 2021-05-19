package Fourmis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoeudTest {
    AntFacadeController appli;
    static final int WIDTH = 13;
    static final int HEIGHT = 19;
    Graphe g;


    @BeforeEach
    void setUp()
    {
        /*
        appli = new ControlFourmis();
        appli.createGrid(WIDTH, HEIGHT);
        appli.createColony(2,2);
         */
        g = new Graphe(WIDTH, HEIGHT);
        g.createColony(2,2);
        g.putObstacle(0,1);
        g.putObstacle(1,0);
    }


    /**
     * Test Unitaire de la classe Noeud
     */
    @Test
    @DisplayName(("Etat du noeud"))
    void testStateNoeud(){
        Noeud.STATE etat_colonnie = g.getNoeud(2,2).getNoeudState();
        Noeud.STATE etat_obstacle = g.getNoeud(0,1).getNoeudState();
        Noeud.STATE etat_free = g.getNoeud(0,10).getNoeudState();
        assertEquals(Noeud.STATE.ANTHILL, etat_colonnie);
        assertEquals(Noeud.STATE.OBSTACLE, etat_obstacle);
        assertEquals(Noeud.STATE.FREE, etat_free);
    }

    @Test
    @DisplayName(("Changement d'état d'un du noeud"))
    void testSetStateNoeud(){
        g.getNoeud(4,4).setNoeudState(Noeud.STATE.ANTHILL);
        Noeud.STATE etat = Noeud.STATE.ANTHILL;
        assertEquals(Noeud.STATE.ANTHILL, etat);
    }

    @Test
    @DisplayName(("Ajout d'un noeud voisin à un noeud"))
    void testAddNoeudVoisin(){
        Noeud n = new Noeud();
        Noeud n_voisin = new Noeud();
        n.addNoeudVoisin(n_voisin);
        n_voisin.addNoeudVoisin(n);

        boolean n_voisin_de_nvoisin= false;
        List<Noeud> voisins = n.getVoisins();
        for(Noeud noeud : voisins){
            if(noeud.equals(n_voisin)){
                n_voisin_de_nvoisin = true;
                break;
            }
        }

        boolean nvoisin_de_n = false;
        List<Noeud> nvoisins = n_voisin.getVoisins();
        for(Noeud noeud : nvoisins){
            if(noeud.equals(n)){
                nvoisin_de_n = true;
                break;
            }
        }

        assertTrue(n_voisin_de_nvoisin);
        assertTrue(nvoisin_de_n);
    }

    @Test
    @DisplayName(("Voisins disponible d'un noeud"))
    void testFreeVoinsinNoeud(){
        //Pas de voisin car le noeud en 0,0 est encerclé
        Noeud n = g.getNoeud(0,0);
        int nb = n.getFreeVoisins().size();
        assertEquals(0, nb);

        //Noeud non encerclé
        Noeud n2 = g.getNoeud(4,4);
        int nb2 = n2.getFreeVoisins().size();
        assertEquals(4, nb2);

        //Noeud en corner
        Noeud noeudCorner = g.getNoeud(HEIGHT-1,WIDTH-1);
        int nbCorner = noeudCorner.getFreeVoisins().size();
        assertEquals(2, nbCorner);
    }

    @Test
    @DisplayName(("Voisins d'un noeud"))
    void testVoisinNoeud(){
        Noeud n = g.getNoeud(0,0);
        List<Noeud>Voisin = n.getVoisins();
        Noeud n1 = g.getNoeud(0,1);
        Noeud n2 = g.getNoeud(1,0);

        boolean voisin_droite = false;
        boolean voisin_bas = false;
        for(Noeud noeud : Voisin){
            if(noeud.equals(n1)){
                voisin_droite = true;
            }
            else if (noeud.equals(n2)){
                voisin_bas = true;
            }
        }
        assertTrue(voisin_bas);
        assertTrue(voisin_droite);
    }

}