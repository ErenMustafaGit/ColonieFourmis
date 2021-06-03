package Fourmis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveIteration {
    private String antLogFile = "";
    private List<List<String>> dataAnt;
    private  List<List<String>> dataNeighbour;
    private ArrayList<Ant> antList;

    public SaveIteration(List<List<String>> dataAnt, List<List<String>> dataNeighbour, ArrayList<Ant> antList){
        this.dataAnt = dataAnt;
        this.dataNeighbour = dataNeighbour;
        this.antList = antList;
    }


    /**
     * Permet de faire l'étape d'enregistrement sur les fourmis lorsque record == true dans la fonction play
     * @param iteration répresente à quel itération les données concorde
     */
    public void recordProcess(int iteration) {
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
            dataAnt.add(Arrays.asList(String.valueOf(iteration), ant.toString(), ant.getPosition().toString(), "FC[" + foodCollected + "]",
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
     * Permet de définir le chemin de sauvegarde du fichier contenant les informations des fourmis et de leur noeud voisin
     * par itération, le nom du fichier est sous forma .csv (donc "nomdufichier.csv" est attendu comme String).
     * @param antLogFile nom du fichier souhaiter
     *                   Ex : "nomdufichier.csv"
     */
    public void setSaveLocation(String antLogFile){
        this.antLogFile = antLogFile;
    }

    /**
     * Permet de faire l'étape d'écriture du fichier .csv lorsque record == true dans la fonction play
     * @throws IOException
     */
    public void writeProcess() throws IOException {
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
        for (List<String> rowData : dataAnt) {
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
        //Création du fichier terminé
    }
}
