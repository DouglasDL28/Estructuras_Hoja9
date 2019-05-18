import java.util.ArrayList;

import static org.junit.Assert.*;

public class GraphTest {

    ArrayList<Edge> edges = new ArrayList<>();

    @org.junit.Test
    public void addEdge() {
        Graph grafo = new Graph(4);

        edges.add(new Edge(1,1,5));
        edges.add(new Edge(2,1,5));
        edges.add(new Edge(1,2,5));
        edges.add(new Edge(3,2,5));
        edges.add(new Edge(3,1,5));
        edges.add(new Edge(1,3,5));

        edges.forEach(it -> {
            grafo.addEdge(it);
        });

        System.out.println(grafo.toString());
    }

    @org.junit.Test
    public void removeEdge() {
        Graph grafo = new Graph(4);

        edges.add(new Edge(1,1,5));
        edges.add(new Edge(2,1,5));
        edges.add(new Edge(1,2,5));
        edges.add(new Edge(3,2,5));
        edges.add(new Edge(3,1,5));
        edges.add(new Edge(1,3,5));

        edges.forEach(it -> {
            grafo.addEdge(it);
        });

        System.out.println("Antes de removeEdge.");
        System.out.println(grafo.toString());

        grafo.removeEdge(1,1);

        System.out.println("Después de removeEdge.");
        System.out.println(grafo.toString());

    }
}