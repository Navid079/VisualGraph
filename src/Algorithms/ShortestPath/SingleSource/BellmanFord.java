package Algorithms.ShortestPath.SingleSource;

import Algorithms.Algorithm;
import Algorithms.Command;
import Algorithms.Note;
import DS.Edge;
import DS.Graph;
import DS.Node;
import GUI.App;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BellmanFord implements Algorithm {

    private final App app;
    private final Graph graph;

    private final int[] distances;
    private final Node[] predecessors;
    private final Edge[] path;

    public BellmanFord(App app, Graph graph){
        ref.clear();

        this.app = app;
        this.graph = graph;

        ref.add(app);

        distances = new int[graph.vertices.size()];
        Arrays.fill(distances, Integer.MAX_VALUE);

        predecessors = new Node[graph.vertices.size()];
        path = new Edge[graph.vertices.size()];
    }

    @Override
    public void run(Node start) {
        commandList.clear();
        noteList.clear();

        distances[graph.vertices.indexOf(start)] = 0;

        for(int i = 0; i < graph.vertices.size() - 2; i++){
            relax();
        }

        for(ArrayList<Edge> list : graph.edges){
            for(Edge e : list){
                int from = graph.vertices.indexOf(graph.fromWhere(e));
                int to = graph.vertices.indexOf(e.toWhere());

                if(distances[from] + e.weight < distances[to]){
                    e.setColor(Color.RED);
                    commandList.add(new Command("SET EDGE " + graph.fromWhere(e).getLabel() + "->" + e.toWhere().getLabel() + " COLOR RED", graph, app));

                    noteList.add(new Note("Error: A negative weight cycle found", commandList.size(), this));
                    app.render();
                    return;
                }
            }
        }
    }

    private void relax() {
        for(ArrayList<Edge> list : graph.edges){
            for(Edge e : list){
                int from = graph.vertices.indexOf(graph.fromWhere(e));
                int to = graph.vertices.indexOf(e.toWhere());

                if(distances[from] + e.weight < distances[to]){
                    if(predecessors[to] != null){
                        path[to].setColor(Color.BLACK);
                        commandList.add(new Command("SET EDGE " + graph.fromWhere(path[to]).getLabel() + "->" + path[to].toWhere().getLabel() + " COLOR BLACK", graph, app));
                        noteList.add(new Note("Edge " + graph.fromWhere(path[to]).getLabel() + "->" + path[to].toWhere().getLabel() + " failed.", commandList.size(), this));
                    }

                    e.setColor(Color.BLUE);

                    commandList.add(new Command("SET EDGE " + graph.fromWhere(e).getLabel() + "->" + e.toWhere().getLabel() + " COLOR BLUE", graph, app));
                    noteList.add(new Note("Edge " + graph.fromWhere(e).getLabel() + "->" + e.toWhere().getLabel() + " is found.", commandList.size(), this));

                    distances[to] = distances[from] + e.weight;
                    predecessors[to] = graph.vertices.get(from);
                    path[to] = e;

                    app.render();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }

    @Override
    public void save() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save Result...");

        if(fc.showSaveDialog(app) == JFileChooser.APPROVE_OPTION){
            File save = fc.getSelectedFile();
            FileWriter csv;
            try {
                csv = new FileWriter(save);
            } catch (IOException e) {
                return;
            }
            int[][] mat = new int[graph.vertices.size()][graph.vertices.size()];
            for(Edge e : path){
                int i = graph.vertices.indexOf(graph.fromWhere(e));
                int j = graph.vertices.indexOf(e.toWhere());

                mat[i][j] = e.weight;
            }
            for(int[] list : mat){
                for(int w : list){
                    try {
                        csv.append(String.valueOf(w));
                        csv.append(',');
                    } catch (IOException ignored) {}
                }
                try {
                    csv.append("\n");
                } catch (IOException ignored) {}
            }
            try {
                csv.close();
            } catch (IOException ignored) {}
        }
    }

    @Override
    public void show(Node node1, Node node2) {

    }
}
