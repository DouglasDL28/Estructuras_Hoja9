import javafx.scene.control.Tab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static String menu = "\n MENÚ: \n" +
            "\t 1. Ver rutas más cortas de un lugar a otro. \n" +
            "\t 2. Centro de grafo. \n" +
            "\t 3. Eliminar ruta entre dos ejes y ver rutas nuevas. \n" +
            "\t 4. Salir del programa. \n";

    public static void main(String[] args) {
        boolean wantsToContinue = true;
        Scanner input = new Scanner(System.in);

        ArrayList<String> destinos = new ArrayList<>();

        ArrayList<Edge> edges = new ArrayList<>();

        //Lectura de archivo guategrafos.txt
        try {
            Stream<String> lines = Files.lines(
                    Paths.get("guategrafo.txt"),
                    StandardCharsets.UTF_8
            );
            lines.forEach(line -> {
                String[] parts = line.toUpperCase().replace(", ", ",").split(",");

                if (parts.length == 3){
                    String start = parts[0];
                    String end = parts[1];
                    int weight = Integer.parseInt(parts[2]);

                    //Revisa si la ciudad ya ha sido agregada anteriormente. Si no, agrega a destinos.
                    if(!destinos.contains(start)){
                        destinos.add(start);
                    }

                    if(!destinos.contains(end)){
                        destinos.add(end);
                    }

                    //Agrega los edges a la lista de edges.
                    edges.add(new Edge(destinos.indexOf(start), destinos.indexOf(end), weight));
                }
            });

            System.out.println("Se han agregado los pacientes al Priority Queue con éxito.");
            destinos.forEach(it ->  System.out.println(it) );

        } catch (IOException exception) {
            System.out.println("Error!");
        }

        Graph grafo = new Graph(destinos.size());

        edges.forEach(it -> {
            grafo.addEdge(it);
        });



        do {
            System.out.println(menu);
            int option = input.nextInt();
            input.nextLine();

            switch (option){

                // Preguntar nombre de ciudad origen y ciudad destino. Devolver ruta.
                case 1: {
                    int n = grafo.getAdjMatrix().length;
                    FloydWarshallSolver solver = new FloydWarshallSolver(grafo.getAdjMatrix());
                    double[][] dist = solver.getApsgpMatrix();

                    int i =0, j = 0;

                    boolean validado = false;
                    do {
                        System.out.println("Ingrese ciudad de origen: ");
                        String origen = input.nextLine().toUpperCase();

                        System.out.println("Ingrese ciudad de destino: ");
                        String destino = input.nextLine().toUpperCase();

                        if (destinos.contains(origen) && destino.contains(destino)){
                             i = destinos.indexOf(origen);
                             j = destino.indexOf(destino);
                            validado = true;
                        } else {
                            System.out.println("La ciudad no es válida.");
                        }

                    }while (!validado);

                    List<Integer> ruta = solver.reconstructShortestPath(i, j);

                    List<String> path = new ArrayList<>();
                    ruta.forEach(it -> path.add(destinos.get(it)));

                    String str;
                    if (path == null) {
                        str = "TIENE ∞ SOLUCIONES! (negative cycle case)";
                    } else if (path.size() == 0) {
                        str = String.format("NO EXISTE (No hay camino de %s a %s)", destinos.get(i), destinos.get(j));
                    } else {
                        str = String.join(" -> ", path.stream()
                                .map(Object::toString)
                                .collect(java.util.stream.Collectors.toList()));
                        str = "es: [" + str + "]";
                    }

                    System.out.printf("El camino más corto de %s a %s con distancia %f %s\n", destinos.get(i), destinos.get(j), dist[i][j], str);

                    break;
                }

                //Encontrar y devolver el centro del grafo.
                case 2: {
                    int n = grafo.getAdjMatrix().length;
                    FloydWarshallSolver solver = new FloydWarshallSolver(grafo.getAdjMatrix());
                    double[][] dist = solver.getApsgpMatrix();

                    HashMap<Integer,Double> eccentricities = new HashMap<>();

                    //Encontrar eccentricities de cada fila.
                    for (int i = 0; i < n; i++) {
                        eccentricities.put(i, 0.0);
                        for (int j = 0; j < n; j++) {
                            double k = dist[i][j];
                            if (eccentricities.get(i ) < k){
                                eccentricities.replace(i,eccentricities.get(i), k);
                            }
                        }
                    }

                    String centerOfGraph = null;
                    double current = 0.0;

                    for ( Map.Entry<Integer, Double> entry : eccentricities.entrySet()) {
                        Integer key = entry.getKey();
                        Double value = entry.getValue();

                        if(current < value){
                            centerOfGraph = destinos.get(key);
                        }
                    }

                    System.out.println("El centro del grafo es " + centerOfGraph);

                    break;
                }

                // Modificar algun valor en el grafo.
                case 3: {
                    int n = grafo.getAdjMatrix().length;

                    int i =0, j = 0;

                    boolean validado = false;
                    do {
                        System.out.println("Ingrese ciudad de origen: ");
                        String origen = input.nextLine().toUpperCase();

                        System.out.println("Ingrese ciudad de destino: ");
                        String destino = input.nextLine().toUpperCase();

                        if (destinos.contains(origen) && destino.contains(destino)){
                            i = destinos.indexOf(origen);
                            j = destino.indexOf(destino);
                            validado = true;
                        } else {
                            System.out.println("La ciudad no es válida.");
                        }

                    }while (!validado);

                    grafo.removeEdge(i,j);

                    System.out.println("Se ha modificado la ruta!");

                    FloydWarshallSolver solver = new FloydWarshallSolver(grafo.getAdjMatrix());
                    double[][] dist = solver.getApsgpMatrix();

                    List<Integer> ruta = solver.reconstructShortestPath(i, j);

                    List<String> path = new ArrayList<>();
                    ruta.forEach(it -> path.add(destinos.get(it)));

                    String str;
                    if (path == null) {
                        str = "TIENE ∞ SOLUCIONES! (negative cycle case)";
                    } else if (path.size() == 0) {
                        str = String.format("NO EXISTE (No hay camino de %s a %s)", destinos.get(i), destinos.get(j));
                    } else {
                        str = String.join(" -> ", path.stream()
                                .map(Object::toString)
                                .collect(java.util.stream.Collectors.toList()));
                        str = "es: [" + str + "]";
                    }

                    System.out.printf("El camino más corto de %s a %s con distancia %f %s\n", destinos.get(i), destinos.get(j), dist[i][j], str);

                    break;
                }

                //Salir del programa.
                case 4: {
                    wantsToContinue = false;
                    break;
                }
            }

        }while (wantsToContinue);
    }
}