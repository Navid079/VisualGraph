package Algorithms.MST;

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

public class Kruskal implements Algorithm {

    private final Graph graph;
    private final App app;
    private final ArrayList<Node> contain = new ArrayList<>();
    private final ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

    public Kruskal(App app, Graph graph){
        ref.clear();

        this.app = app;
        ref.add(app);
        this.graph = graph;
        for(int i = 0; i < graph.vertices.size(); i++){
            edges.add(new ArrayList<>());
        }
    }

    @Override
    public void run(Node start) {
        commandList.clear();
        noteList.clear();

        contain.add(start);
        start.setColor(Color.BLUE);
        commandList.add(new Command("SET NODE " + start.getLabel() + " COLOR BLUE", graph, app));
        noteList.add(new Note("Node " + start.getLabel() + " is spanned", commandList.size(), this));
        app.render();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        Edge e = findEdge();
        while(e != null){
            contain.add(e.toWhere());
            edges.get(graph.vertices.indexOf(graph.fromWhere(e))).add(e);
            if(e.isBidirectional){
                edges.get(graph.vertices.indexOf(e.toWhere())).add(e.pair);
            }

            e.toWhere().setColor(Color.BLUE);
            e.setColor(Color.CYAN);

            commandList.add(new Command("SET EDGE " + graph.fromWhere(e) + "->" + e.toWhere().getLabel() + " COLOR CYAN", graph, app));
            noteList.add(new Note("Edge " + graph.fromWhere(e).getLabel() + "->" + e.toWhere().getLabel() + " is in MST", commandList.size(), this));
            app.render();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            commandList.add(new Command("SET NODE " + e.toWhere().getLabel() + " COLOR BLUE", graph, app));
            noteList.add(new Note("Node " + e.toWhere().getLabel() + " is spanned", commandList.size(), this));
            app.render();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            e = findEdge();
        }
    }

    private Edge findEdge() {
        int minWeight = Integer.MAX_VALUE;
        Edge min = null;
        for(Node n : contain){
            int index = graph.vertices.indexOf(n);
            for(Edge edge : graph.edges.get(index)){
                if(contain.contains(edge.toWhere())) continue;
                if(minWeight > edge.weight){
                    minWeight = edge.weight;
                    min = edge;
                }
            }
        }

        return min;
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
            for(int i = 0; i < graph.vertices.size(); i++){
                ArrayList<Edge> list = edges.get(i);
                for(int j = 0; j < graph.vertices.size(); j++){
                    Edge e = null;
                    for(Edge edge : list){
                        if(edge.toWhere().equals(graph.vertices.get(j))){
                            e = edge;
                            break;
                        }
                    }
                    if(e == null)
                        mat[i][j] = 0;
                    else {
                        mat[i][j] = e.weight;
                    }

                }
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
