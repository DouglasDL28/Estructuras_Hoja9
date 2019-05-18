import java.util.Arrays;

import static java.lang.String.format;

public class Graph {
    private double[][] adjMatrix;
    private int numVertices;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new double[numVertices][numVertices];

        for(int i=0; i <= numVertices -1; i++){
            for (int j=0; j <= numVertices - 1; j++){
                if (i == j){
                    adjMatrix[i][j] = 0;
                }
                else{
                    adjMatrix[i][j] = 9999;
                }
            }
        }
    }

    public void addEdge(Edge edge) {
        int startVertex = edge.startVertex;
        int endVertex = edge.endVertex;
        int weight = edge.weight;

        adjMatrix[startVertex][endVertex] = weight;
    }

    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = 9999;
    }

    public double isEdge(int i, int j) {
        return adjMatrix[i][j];
    }


    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public String toString() {
        String res = "";

        for(int i=0; i<= numVertices - 1; i++){
            for (int j =0; j <= numVertices -1; j++){
                res += adjMatrix[i][j] + "  ";
            }
            res += "\n";
        }

        return res;
    }

}