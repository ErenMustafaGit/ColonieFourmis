package Fourmis;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int width;
    private int height;
    private List<Node> nodeList;


    /**
     * Créé un graphe, le remplis de noeud, puis ajoute les noeuds voisins des noeuds
     * @param width : longueur
     * @param height : hauteur
     */
    public Graph(Integer width, Integer height){
        nodeList = new ArrayList<>();
        this.width = width;
        this.height = height;

        //Ajout des noeuds dans le graphe
        //Erreur dans le sujet : Height et witdh inversé
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Node n = new Node();
                nodeList.add(n);
            }
        }

        //Instaciation des liens entre les Noeuds -> Liste de voisins
        for(int x = 0; x < height - 1;x++){
            for(int y = 0;y<width - 1;y++){
                //Récuperation Noeud actuel
                Node n = this.getNode(x,y);

                //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
                Node nRight = this.getNode(x+1,y);
                //Noeud nRight = this.graphe_tab.get(x+y*width+1);


                //Récuperation Noeud se trouvant en bas du Noeud actuel (n)
                Node nDown = this.getNode(x,y+1);
                //Noeud nDown = this.graphe_tab.get(x+(y+1)*width);

                //Ajout dans le noeud actuel (n), les voisins du bas et de droite
                n.addNoeudVoisin(nRight);
                n.addNoeudVoisin(nDown);

                //Ajout dans les noeuds de droite et du bas, le voisin : noeud actuel (n)
                nRight.addNoeudVoisin(n);
                nDown.addNoeudVoisin(n);
            }
        }


        //Noeud de tout à droite
        for(int x = 0; x<height-1; x++){
            //Récuperation Noeud actuel
            Node n = this.getNode(x,width-1);

            //Récuperation Noeud se trouvant en bas du Noeud actuel (n)
            Node nDown = this.getNode(x+1,width-1);

            //Ajout dans le noeud actuel (n), le voisins du bas
            n.addNoeudVoisin(nDown);

            //Ajout dans le noeuds du bas, le voisin : noeud actuel (n)
            nDown.addNoeudVoisin(n);
        }

        //Noeud de tout en bas
        for(int y = 0; y<width-1; y++){
            //Récuperation Noeud actuel
            Node n = this.getNode(height-1,y);

            //Récuperation Noeud se trouvant à droite du Noeud actuel (n)
            Node nRight = this.getNode(height-1,y+1);

            //Ajout dans le noeud actuel (n), le voisins de droite
            n.addNoeudVoisin(nRight);

            //Ajout dans le noeuds de droite, le voisin : noeud actuel (n)
            nRight.addNoeudVoisin(n);
        }
    }


    /**
     * Permet d'obtenir un noeud à partir d'une coordonné (x=row;y=column)
     * @param row : la ligne
     * @param column : la colonne
     * @return retourne un Noeud
     */
    public Node getNode(Integer row, Integer column){
        return nodeList.get(column + row * width);
    }

    /**
     * Permet de placer un obstacle à une coordonnée donné (x=row;y=column)
     * @param row : la ligne
     * @param column : la colonne
     */
    public void putObstacle(Integer row, Integer column){
        Node n = nodeList.get(column+ row * width);

        //Si le noeud ne possède pas de fourmillière ou de nourriture
        if(n.getNodeState() != State.ANTHILL && n.getFood() <= 0){
            n.setNodeState(State.OBSTACLE);

            //SINON
        } else{
            throw new IllegalArgumentException("Impossible de placer un obstacle sur une fourmillière");
        }

    }

    /**
     * Permet de récuperer les noeuds contenue dans le graphe
     * @return retourne une Liste de Noeud
     */
    public List<Node> getNodeList(){
        return this.nodeList;
    }

    /**
     * Renvoie la longueur de la grille du graphe
     * @return renvoie la longueur de la grille du graphe
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Renvoie la hauteur de la grille du graphe
     * @return renvoie la hauteur de la grille du graphe
     */
    public int getHeight(){
        return this.height;
    }

}
