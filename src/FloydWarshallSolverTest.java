import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FloydWarshallSolverTest {

    @Test
    public void FloydWarshall() {
        // Construct graph.
        int n = 7;
        double[][] m = FloydWarshallSolver.createGraph(n);

        // Add some edge values.
        m[0][1] = 2;
        m[0][2] = 5;
        m[0][6] = 10;
        m[1][2] = 2;
        m[1][4] = 11;
        m[2][6] = 2;
        m[6][5] = 11;
        m[4][5] = 1;
        m[5][4] = -2;

        FloydWarshallSolver solver = new FloydWarshallSolver(m);
        double[][] dist = solver.getApsgpMatrix();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                System.out.printf("El camino más corto del nodo %d al nodo %d is %.3f\n", i, j, dist[i][j]);

        System.out.println();

        // Reconstructs the shortest paths from all nodes to every other nodes.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                List<Integer> path = solver.reconstructShortestPath(i, j);
                String str;
                if (path == null) {
                    str = "TIENE ∞ INFINITAS! (negative cycle case)";
                } else if (path.size() == 0) {
                    str = String.format("NO EXISTE (node %d doesn't reach node %d)", i, j);
                } else {
                    str = String.join(" -> ", path.stream()
                            .map(Object::toString)
                            .collect(java.util.stream.Collectors.toList()));
                    str = "es: [" + str + "]";
                }

                System.out.printf("El camino más corto de %d a %d %s\n", i, j, str);
            }
        }

    }

}