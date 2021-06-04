package Fourmis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
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

    @Test
    @DisplayName("Même position que la colonie quand ils sont crée + Création seul")
    void getPosition() {
        appli.createWorkers(4);
        for(Ant worker : appli.getAntList()){
            if(worker instanceof Worker)
                assertEquals(appli.getGraph().getNode(2,2), worker.getPosition());
        }

        Node2D position = appli.getGraph().getNode(5,5);
        Worker worker = new Worker(position, null);
        assertEquals(position, worker.getPosition());
    }

    @Test
    void setPosition() {
        Node2D position = appli.getGraph().getNode(5,5);
        Worker worker = new Worker(position, null);
        Node2D direction = appli.getGraph().getNode(10,10);
        worker.setPosition(direction);
        assertEquals(direction, worker.getPosition());
    }

    @Test
    void move() {
        AntHill colony = new AntHill(appli.getGraph().getNode(5,0));
        appli.putFood(4,5, 100);
        appli.putFood(5,4, 10);
        appli.putFood(6,5, 10);

        Node2D position = appli.getGraph().getNode(5,5);
        Worker worker = new Worker(position, colony);
        worker.move();

        //Il va vers le noeud avec le plus de nourriture dans ses voisins
        Node2D newPosition = appli.getGraph().getNode(4,5);
        assertEquals(newPosition, worker.getPosition());
    }

    @Test
    void collect() {
        Node2D position = appli.getGraph().getNode(4,4);
        AntHill colony = new AntHill(appli.getGraph().getNode(0,0));
        colony.setCollectCapacity(10);
        appli.putFood(4,4, 100);

        Worker worker = new Worker(position, colony);
        assertEquals(100, position.getFood());
        worker.collect();
        assertEquals(90, position.getFood());

        //Il ne ramasse de la nourriture que si il en a pas
        worker.collect();
        assertEquals(90, position.getFood());
    }

    @Test
    void putPheromone() {
        AntHill colony = new AntHill(appli.getGraph().getNode(0,0));
        colony.setPheromoneQuantity(10);

        Node2D position = appli.getGraph().getNode(4,4);
        Worker worker = new Worker(position, colony);

        assertEquals(0, position.getPheromone().size());
        worker.putPheromone();
        assertEquals(1, position.getPheromone().size());
        worker.putPheromone();
        assertEquals(2, position.getPheromone().size());
    }

    @Test
    void getFoodCollected() {
        Node2D position = appli.getGraph().getNode(4,4);
        AntHill colony = new AntHill(appli.getGraph().getNode(0,0));
        colony.setCollectCapacity(10);
        appli.putFood(4,4, 100);

        Worker worker = new Worker(position, colony);
        assertEquals(0, worker.getFoodCollected());
        worker.collect();
        assertEquals(colony.getCollectCapacity(), worker.getFoodCollected());

        //Il ne ramasse de la nourriture que si il en a pas
        worker.collect();
        assertEquals(colony.getCollectCapacity(),worker.getFoodCollected());
    }

    @Test
    void addToRecordsPath() {
        Node2D position = appli.getGraph().getNode(4,4);
        AntHill colony = new AntHill(appli.getGraph().getNode(0,0));
        colony.setCollectCapacity(10);
        appli.putFood(4,4, 100);

        //Ouvriere avec de la nourriture
        Worker worker = new Worker(position, colony);
        worker.collect();

        //Ajout de 2 chemin dans son historique qu'il va devoir re-parcourir
        Node2D n1 = appli.getGraph().getNode(4,3);
        Node2D n2 = appli.getGraph().getNode(4,3);
        worker.addToRecordsPath(n1);
        worker.addToRecordsPath(n2);

        worker.move();
        assertEquals(n1, worker.getPosition());
        worker.move();
        assertEquals(n2, worker.getPosition());

    }
}