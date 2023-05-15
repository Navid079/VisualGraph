package Algorithms.ShortestPath.SingleSource;

import Algorithms.Algorithm;
import Algorithms.Command;
import Algorithms.Note;
import DS.Edge;
import DS.Graph;
import DS.Node;
import GUI.App;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Dijkstra implements Algorithm {

    private App app;
    private Graph graph;

    private int[] distances;
    private Node[] predecessors;
    private Edge[] path;

    private ArrayList<Node> pending = new ArrayList<>();

    public Dijkstra(App app, Graph graph){
        ref.clear();

        this.app = app;
        this.graph = graph;

        ref.add(app);

        distances = new int[graph.vertices.size()];
        predecessors = new Node[graph.vertices.size()];
        path = new Edge[graph.vertices.size()];

        Arrays.fill(distances, Integer.MAX_VALUE - 1);
        pending.addAll(graph.vertices);
    }

    @Override
    public void run(Node start) {
        commandList.clear();
        noteList.clear();

        distances[graph.vertices.indexOf(start)] = 0;

        while(!pending.isEmpty()){
            Node n = getNode();

            pending.remove(n);
            ArrayList<Edge> list = graph.edges.get(graph.vertices.indexOf(n));
            for(Edge e : list){

                if(e.weight < 0){
                    e.setColor(Color.RED);
                    commandList.add(new Command("SET EDGE " + graph.fromWhere(e).getLabel() + "->" + e.toWhere().getLabel() + " COLOR RED", graph, app));

                    noteList.add(new Note("Error: A negative weight edge found", commandList.size(), this));
                    app.render();
                    return;
                }

                if(!pending.contains(e.toWhere())) continue;

                int from = graph.vertices.indexOf(graph.fromWhere(e));
                int to = graph.vertices.indexOf(e.toWhere());

                if(distances[from] + e.weight < distances[to]){
                    if(predecessors[to] != null){
                        path[to].setColor(Color.BLACK);
                        commandList.add(new Command("SET EDGE " + graph.fromWhere(path[to]).getLabel() + "->" + path[to].toWhere().getLabel() + " COLOR BLACK", graph, app));
                        noteList.add(new Note("Edge " + graph.fromWhere(path[to]).getLabel() + "->" + path[to].toWhere().getLabel() + " failed.", commandList.size(), this));
                    }

                    distances[to] = distances[from] + e.weight;
                    predecessors[to] = graph.vertices.get(from);

                    e.setColor(Color.BLUE);
                    commandList.add(new Command("SET EDGE " + graph.fromWhere(e).getLabel() + "->" + e.toWhere().getLabel() + " COLOR BLUE", graph, app));
                    noteList.add(new Note("Edge " + graph.vertices.get(from).getLabel() + "->" + e.toWhere().getLabel() + "found", commandList.size(), this));

                    path[to] = e;

                    app.render();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }

    private Node getNode() {
        int mn = Integer.MAX_VALUE;
        int index = -1;
        for(Node n : pending){
            if(distances[graph.vertices.indexOf(n)] < mn){
                index = graph.vertices.indexOf(n);
                mn = distances[index];
            }
        }

        return graph.vertices.get(index);
    }

    @Override
    public void save() {

    }

    @Override
    public void show(Node node1, Node node2) {

    }
}
