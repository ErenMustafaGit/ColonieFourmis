package Fourmis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ControlAnt implements AntFacadeController {
    private Graph graph;
    private ArrayList<Ant> antList = new ArrayList<>();
    private int collectCapacity;
    private int pheromoneQuantity;
    private int evaporationQuantity;
    //variables csv
    private List<List<String>> dataFourmis = new ArrayList<>();
    private List<List<String>> dataNeighbour = new ArrayList<>();
    private String antLogFile = "";


    /**
     * Retourne la liste de fourmis de l'instance
     * @return this.antList : la liste de fourmis de l'application, avec encapsulation.
     */
    public ArrayList<Ant> getAntList(){
        return new ArrayList<>(this.antList);
    }


    /**
     * Créé le fichier d'historique des fourmis.
     * @param row numero de ligne
     * @param column numero de la colonne
     * @param quantity quantité de phéromone à déposer sur le noeud
     */
    public void putPheromone(int row, int column, int quantity) {
        Pheromone pheromone = new Pheromone(quantity, null);
        this.graph.getNoeud(row, column).addPheromone(pheromone);
    }

    /**
     * Retourne le graphe de l'instance de contrôle
     * @return this.graphe : le graphe de l'application
     */
    public Graph getGraph(){
        return this.graph;
    }

    /**
     * Fixe les paramètres de l'application.
     * @param evaporationParam rythme d'évaporation des phéromones. La valeur par défaut est 1 par itération.
     * @param foodParam quantité maximale de nourriture que peut transporter une fourmi ouvrière.
     * @param pheromoneParam quantité de phéromone déposée par les fourmis lors de leur passage dans une cellule.
     */
    @Override
    public void setParameters(int evaporationParam, int foodParam, int pheromoneParam) {
        //s'il n'y a pas de reine.
        if(antList.isEmpty()){
            this.evaporationQuantity = evaporationParam;
            this.collectCapacity = foodParam;
            this.pheromoneQuantity = pheromoneParam;
        }
        //si les reines sont déjà existente.
        else {
            for(Ant ant : antList){
                if(ant instanceof AntHill){
                    ((AntHill) ant).setCollectCapacity(foodParam);
                    ((AntHill) ant).setPheromoneQuantity(pheromoneParam);
                    this.evaporationQuantity = evaporationParam;
                }
            }
        }

    }

    /**
     * Permet de créé un graphe, respectant une taille donné (donc une grille)
     * @param width : largeur de la grille
     * @param height : hauteur de la grille
     */
    @Override
    public void createGrid(int width, int height) {
        this.graph = new Graph(width, height);
    }

    /**
     * Permet de placer un obstacle à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    @Override
    public void putObstacle(int row, int column) {
        this.graph.putObstacle(row, column);
    }

    /**
     * Place de la nourriture dans une cellule de la grille.
     * @param row ordonnée de la cellule
     * @param column abscisse de la cellule
     * @param quantity quantité de nourriture déposée
     */
    @Override
    public void putFood(int row, int column, int quantity) {
        Node n = this.graph.getNoeud(row, column);
        if(n.getNodeState() == Node.STATE.FREE){
            n.setFood(quantity);
        }else{
            throw new IllegalArgumentException("Placement de la nourriture impossible sur ce noeud");
        }
    }

    /**
     * Place la reine à une coordonnée donné (x=row;y=column)
     * @param row : la ligne (x)
     * @param column : la colonne (y)
     */
    @Override
    public void createColony(int row, int column) {
        AntHill antHill = new AntHill(this.graph.getNoeud(row,column));
        antHill.setPheromoneQuantity(this.pheromoneQuantity);
        antHill.setCollectCapacity(this.collectCapacity);
        antList.add(antHill);
    }

    /**
     * Créé une quantité de soldat à l'emplacement de la fourmilière (donc à la position de la reine
     * @param amount : quantité de soldat souhaitée
     */
    @Override
    //à reverifier
    public void createSoldiers(int amount) {
        ArrayList<Ant> tempSoldier = new ArrayList<>();
        for(Ant ant : this.antList){
            //Si la fourmis est une reine (donc colonie)
            if(ant instanceof AntHill){
                for(int i = 0; i < amount; i++){
                    Soldier s = new Soldier(ant.getPosition(), (AntHill)ant);
                    tempSoldier.add(s);
                }
            }
        }
        antList.addAll(tempSoldier);
    }

    /**
     * Créé un ensemble de fourmis ouvrières
     * @param amount nombre d'ouvrière's à créer
     */
    @Override
    public void createWorkers(int amount) {
        ArrayList<Ant> tempWorker = new ArrayList<>();
        for(Ant ant : this.antList){

            //Si la fourmis est une reine (donc colonie)
            if(ant instanceof AntHill){
                for(int i = 0; i < amount; i++){
                    Worker worker = new Worker(ant.getPosition(), (AntHill)ant);
                    tempWorker.add(worker);
                }
            }
        }
        antList.addAll(tempWorker);
    }

    /**
     * Créé le fichier d'historique des fourmis.
     * @param antLogFile nom du fichier (avec extension) pour l'enregistrement des états des fourmis
     */
    @Override
    public void setAntFile(String antLogFile) {
        this.antLogFile = antLogFile;
    }

    /**
     * Déclenche des itérations.
     * @param duration nombre d'itérations à effectuer
     * @return un tableau de vecteurs de 5 bits. La dimension du tableau est celle de la grille.
     *  - le bit n° 0 vaut true si le noeud correspondant de la grille abrite la fourmilière ;
     *  - le bit n° 1 vaut true si le noeud est occupé par un obstacle ;
     *  - le bit n° 2 vaut true s'il y a au moins une fourmi-soldat sur le noeud ;
     *  - le bit n° 3 vaut true s'il y a au moins une fourmi-ouvrière sans nourriture sur le noeud ;
     *  - le bit n° 4 vaut true s'il y a au moins une ouvrière portant de la nourriture sur le noeud.
     *  - le bit n° 5 vaut true s'il y a de la nourriture sur le noeud ;
     *  - le bit n° 6 vaut true s'il y a des phéromones sur le noeud.
     */
    @Override
    public BitSet[][] play(int duration, boolean record) throws IOException {

        if(record){
            recordProcess(0);
        }

        //BitSet[][] bit_play = new BitSet[this.graphe.getWidth()][this.graphe.getHeight()];
        BitSet[][] bit_play = new BitSet[this.graph.getHeight()][this.graph.getWidth()];



        //Déplacement des fourmis + evaporation des phéromones pour chaque itération
        for(int iteration = 0 ; iteration < duration; iteration++){
            //Evaporation des phéromones
            //Récupération de tout les noeuds du graphe
            for(Node node : this.graph.getNoeudList()){
                for(Pheromone pheromone : node.getPheromone()){
                    pheromone.setQuantity(pheromone.getQuantity() - evaporationQuantity);
                }
                node.updatePheromone();
            }

            //Déplacer toutes les fourmis
            for(Ant ant : this.antList){
                ant.move();
            }
            if(record){
                recordProcess(iteration + 1);
            }
        }

        //Remplissage du BitSet[][]
        for(int row = 0; row < this.graph.getHeight(); row++){
            for(int column = 0; column < this.graph.getWidth(); column++){
                bit_play[row][column]=new BitSet(7);

                //Compteur du nombre de soldat et d'ouvrier
                int compteurSoldier = 0;
                int compteurWorker = 0;

                //Parcours de toutes les fourmis
                for(Ant ant : this.antList){

                    //Si c'est un soldat dans la position actuel
                    if(ant instanceof Soldier && ant.getPosition() == this.graph.getNoeud(row, column)){
                        compteurSoldier++;
                        if(compteurSoldier > 0){
                            bit_play[row][column].set(2, true);
                        }
                    }

                    //Si c'est un ouvrier dans la position actuel
                    if(ant instanceof Worker && ant.getPosition() == this.graph.getNoeud(row, column)){
                        compteurWorker++;
                        if(compteurWorker > 0){
                            Worker worker = (Worker)ant;

                            //Si l'ouvrière a de la nourriture collecté
                            if(worker.getFoodCollected() > 0){
                                bit_play[row][column].set(4, true);
                            }
                            //Si l'ouvrière n'a PAS de nourriture collecté
                            else{
                                bit_play[row][column].set(3, true);
                            }
                        }
                    }
                }

                //Présence de colonnie
                if(this.graph.getNoeud(row, column).getNodeState() == Node.STATE.ANTHILL)
                    bit_play[row][column].set(0, true);

                    //Présence d'obstacle
                else if (this.graph.getNoeud(row, column).getNodeState() == Node.STATE.OBSTACLE)
                    bit_play[row][column].set(1, true);

                    //Présence de nourriture
                else if (this.graph.getNoeud(row, column).getFood() > 0)
                    bit_play[row][column].set(5, true);

                    //Présence de phéromone
                else if (this.graph.getNoeud(row, column).getPheromone().size() != 0){
                    bit_play[row][column].set(6, true);
                }

            }
        }

        //Création du fichier
        if(record){
            writeProcess();
        }
        return bit_play;
    }

    /**
     * Permet de faire l'étape d'enregistrement sur les fourmis lorsque record == true dans la fonction play
     * @param iteration répresente à quel itération les données concorde
     */
    private void recordProcess(int iteration) {
        for (Ant ant : this.antList) {
            //Liste temporaire contenant les informations des noeuds voisin de la fourmis
            List<List<String>> tempNeighbor = new ArrayList<>();

            //quantité de nourriture collecté si c'est une ouvrière
            int foodCollected = 0;
            if (ant instanceof Worker) {
                Worker worker = (Worker) ant;
                foodCollected = worker.getFoodCollected();
            }

            //quantité de phéromone sur le noeud où se situe la fourmis
            int quantityOfPheromone = 0;
            for (Pheromone pheromone : ant.getPosition().getPheromone()) {
                quantityOfPheromone += pheromone.getQuantity();
            }

            //Contient les informations de la fourmis
            dataFourmis.add(Arrays.asList(String.valueOf(iteration), ant.toString(), ant.getPosition().toString(), "FC[" + foodCollected + "]",
                    ant.getPosition().getNodeState().toString(), "QF[" + ant.getPosition().getFood() + "]",
                    "QP[" + quantityOfPheromone + "]"));


            //Information concernant les noeuds voisin du noeud courant
            if (ant.getPosition().getVoisins().size() != 0) {
                for (Node node : ant.getPosition().getVoisins()) {
                    //quantité de phéromone sur le noeud voisin en question
                    int quantityOfPheromoneNodeNeighBour = 0;
                    for (Pheromone pheromone : ant.getPosition().getPheromone()) {
                        quantityOfPheromoneNodeNeighBour += pheromone.getQuantity();
                    }
                    //Contient les informations des noeuds voisins de la fourmis
                    tempNeighbor.add(Arrays.asList(node.toString(), node.getNodeState().toString(), "QF[" + node.getFood() + "]",
                            "QP[" + quantityOfPheromoneNodeNeighBour + "]"));

                }
            }
            //Donne les informations à la varaiable dataNeighbour
            dataNeighbour = tempNeighbor;
        }
    }

    /**
     * Permet de faire l'étape d'écriture du fichier .csv lorsque record == true dans la fonction play
     * @throws IOException
     */
    private void writeProcess() throws IOException {
        //Si antLogFile n'a pas étais défini
        //le fichier .csv prend comme nom "a22.csv"
        String file = "a22.csv";
        if(antLogFile != ""){
            file = antLogFile;
        }

        //Initialisation de la création du fichier
        FileWriter csvWriter = new FileWriter(file);
        //En tête
        csvWriter.append("Iteration | Ant | Node | Food collected (FC) | State of node | Food on the node (QF) | Pheromone (QP)\n");
        //Récolte des informations des fourmis
        for (List<String> rowData : dataFourmis) {
            csvWriter.append(String.join(" | ", rowData));
            csvWriter.append("\n");
            csvWriter.append("Neighboors\n");
            //Récolte des informations des noeuds voisins de la fourmis
            for(List<String> rowNeighboor : dataNeighbour){
                csvWriter.append(String.join(" | ", rowNeighboor));
                csvWriter.append("\n");
            }
            csvWriter.append("\n");
        }
        //Fin d'écriture du fichier
        csvWriter.close();
        //Création du fichier fini
    }
}
