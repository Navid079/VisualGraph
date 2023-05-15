package Algorithms.ShortestPath.AllPair;

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

public class FloydWarshall implements Algorithm {

    private final App app;
    private final Graph graph;

    private final long[][] distances;
    private final Node[][] successors;
    private final Edge[][] edges;

    public FloydWarshall(App app, Graph graph){
        ref.clear();

        this.app = app;
        this.graph = graph;

        ref.add(app);

        int size = graph.vertices.size();

        distances = new long[size][size];
        successors = new Node[size][size];
        edges = new Edge[size][size];

        for(long[] row : distances){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
    }

    @Override
    public void run(Node start) {

        commandList.clear();
        noteList.clear();

        for(ArrayList<Edge> list : graph.edges){
            for (Edge e : list){
                int i = graph.vertices.indexOf(graph.fromWhere(e));
                int j = graph.vertices.indexOf(e.toWhere());

                distances[i][j] = e.weight;
                successors[i][j] = e.toWhere();
                edges[i][j] = e;

                commandList.add(new Command("RESET", graph, app));
                graph.reset();

                commandList.add(new Command("SET NODE " + i + " COLOR BLUE", graph, app));
                commandList.add(new Command("SET NODE " + j + " COLOR RED", graph, app));

                graph.fromWhere(e).setColor(Color.BLUE);
                e.toWhere().setColor(Color.RED);

                noteList.add(new Note("New path discovered from " + i + " to " + j, commandList.size(), this));

                app.render();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }

        for(Node n : graph.vertices){
            int i = graph.vertices.indexOf(n);

            distances[i][i] = 0;
            successors[i][i] = n;
            edges[i][i] = null;

            commandList.add(new Command("RESET", graph, app));
            graph.reset();

            commandList.add(new Command("SET NODE " + i + " COLOR BLUE", graph, app));
            n.setColor(Color.BLUE);

            noteList.add(new Note("New path found from " + i + " to " + i, commandList.size(), this));

            app.render();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }

        int size = graph.vertices.size();

        for(int k = 0; k < size; k++){
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(distances[i][j] > distances[i][k] + distances[k][j]){
                        distances[i][j] = distances[i][k] + distances[k][j];
                        successors[i][j] = successors[i][k];
                        edges[i][j] = edges[i][k];

                        commandList.add(new Command("RESET", graph, app));
                        graph.reset();

                        commandList.add(new Command("SET NODE " + i + " COLOR BLUE", graph, app));
                        commandList.add(new Command("SET NODE " + j + " COLOR RED", graph, app));
                        graph.vertices.get(i).setColor(Color.BLUE);
                        graph.vertices.get(j).setColor(Color.RED);

                        noteList.add(new Note("New path discovered from " + i + " to " + j, commandList.size(), this));

                        app.render();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {}
                    }
                }
            }
        }

        app.enableShow();
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
            for(long[] list : distances){
                for(long w : list){
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
        graph.reset();

        node1.setColor(Color.BLUE);
        node2.setColor(Color.RED);

        int i = graph.vertices.indexOf(node1);
        int j = graph.vertices.indexOf(node2);

        Edge t = edges[i][j];
        while(t != null){
            t.setColor(Color.CYAN);
            t = edges[graph.vertices.indexOf(t.toWhere())][j];
        }
        app.render();
    }
}
