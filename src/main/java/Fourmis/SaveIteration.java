package Fourmis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveIteration {
    private String antLogFile = "";
    private List<List<String>> dataAnt;
    private ArrayList<Ant> antList;

    public SaveIteration(List<List<String>> dataAnt, ArrayList<Ant> antList){
        this.dataAnt = dataAnt;
        this.antList = antList;
    }


    /**
     * Permet de faire l'étape d'enregistrement sur les fourmis lorsque record == true dans la fonction play
     * @param iteration répresente à quel itération les données concorde
     */
    public void recordProcess(int iteration) {
        for (Ant ant : this.antList) {

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
            ArrayList<Node> node2DVoisin = new ArrayList<>(ant.getPosition().getVoisins());
            String nodeNeighboor = "Neighbor of " + ant.getPosition().toString();
            if (node2DVoisin.size() != 0) {
                for (Node node2D : node2DVoisin) {
                    //quantité de phéromone sur le noeud voisin en question
                    int quantityOfPheromoneNodeNeighBour = 0;
                    for (Pheromone pheromone : ant.getPosition().getPheromone()) {
                        quantityOfPheromoneNodeNeighBour += pheromone.getQuantity();
                    }
                    //Contient les informations des noeuds voisins de la fourmis
                    nodeNeighboor += "\n"+ node2D.toString() + " | ";
                    nodeNeighboor += node2D.getNodeState().toString() + " | ";
                    nodeNeighboor += "QF"+ "[" + node2D.getFood() + "]"+ " | ";
                    nodeNeighboor += "QP[" + quantityOfPheromoneNodeNeighBour + "]" + " | ";
                }
                dataAnt.add(Arrays.asList(nodeNeighboor+"\n"));
            }
            //Donne les informations à la varaiable dataNeighbour
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
        }
        //Fin d'écriture du fichier
        csvWriter.close();
        //Création du fichier terminé
    }
}
