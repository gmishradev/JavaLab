package dev.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphMain {
    int numberOfVertex;
    List<List<Edge>> adjList;

    public List<List<Edge>> getAdjList() {
        return adjList;
    }

    public GraphMain(int v) {
        numberOfVertex = v;
        adjList = createAdjList(numberOfVertex);
    }

    static class Edge {
        int src;
        int des;
        int weg;

        public Edge(int src, int des, int weg) {
            this.src = src;
            this.des = des;
            this.weg = weg;
        }

        public Edge(int src, int des) {
            this.src = src;
            this.des = des;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "src=" + src +
                    ", des=" + des +
                    ", weg=" + weg +
                    '}';
        }
    }

    public List<List<Edge>> createAdjList(int v) {
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            List<Edge> edgeList = new LinkedList<>();
            graph.add(edgeList);
        }
        graph.get(0).add(new Edge(0, 1, 10));
        graph.get(1).add(new Edge(1, 0, 10));

        graph.get(0).add(new Edge(0, 3, 10));
        graph.get(3).add(new Edge(3, 0, 10));

        graph.get(1).add(new Edge(1, 2, 10));
        graph.get(2).add(new Edge(2, 1, 10));

        graph.get(2).add(new Edge(2, 3, 10));
        graph.get(3).add(new Edge(3, 2, 10));

        graph.get(3).add(new Edge(3, 4, 10));
        graph.get(4).add(new Edge(4, 3, 10));

        graph.get(4).add(new Edge(4, 5, 10));
        graph.get(5).add(new Edge(5, 4, 10));

        graph.get(5).add(new Edge(5, 6, 10));
        graph.get(6).add(new Edge(6, 5, 10));

        graph.get(4).add(new Edge(4, 6, 10));
        graph.get(6).add(new Edge(6, 4, 10));
        return graph;
    }

    public static void main(String[] args) {
        System.out.println("createAdjListGraph");
        GraphMain graphMain = new GraphMain(7);
        System.out.println("printAdjList Graph");
        printAdjList(graphMain);
    }

    private static void printAdjList(GraphMain graphMain) {
        for (List<Edge> edges : graphMain.getAdjList()) {
            for (Edge edge : edges) {
                System.out.println(edge);
            }
        }
    }

}
