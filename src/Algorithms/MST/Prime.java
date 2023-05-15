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
import java.util.Arrays;

public class Prime implements Algorithm {

    private final App app;
    private final Graph graph;

    private final int[] keys;
    private final Node[] froms;

    private final ArrayList<Node> contain = new ArrayList<>();
    private final ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

    public Prime(App app, Graph graph){
        ref.clear();

        this.app = app;
        this.graph = graph;
        ref.add(app);

        keys = new int[graph.vertices.size()];
        froms = new Node[graph.vertices.size()];
        Arrays.fill(keys, Integer.MAX_VALUE);

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

        try{
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        keys[graph.vertices.indexOf(start)] = 0;
        updateKeys();

        Edge e = getEdge();
        while(e != null){
            edges.get(graph.vertices.indexOf(graph.fromWhere(e))).add(e);
            if(e.isBidirectional){
                edges.get(graph.vertices.indexOf(e.toWhere())).add(e.pair);
            }

            contain.add(e.toWhere());
            Node from = graph.fromWhere(e);

            e.toWhere().setColor(Color.BLUE);
            commandList.add(new Command("SET NODE " + e.toWhere().getLabel() + " COLOR BLUE", graph, app));
            noteList.add(new Note("Node " + e.toWhere().getLabel() + " is spanned", commandList.size(), this));

            e.setColor(Color.CYAN);
            commandList.add(new Command("SET EDGE " + from.getLabel() + "->" + e.toWhere().getLabel() + " COLOR CYAN", graph, app));
            noteList.add(new Note("Edge " + from.getLabel() + "->" + e.toWhere().getLabel() + " is in MST", commandList.size(), this));

            app.render();

            try{
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            updateKeys();
            e = getEdge();
        }
    }

    private Edge getEdge() {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < keys.length; i++){
            if(contain.contains(graph.vertices.get(i))) continue;

            if(keys[i] < min){
                index = i;
                min = keys[i];
            }
        }
        if(index == -1)
            return null;
        System.out.println(index);

        return graph.getEdge(froms[index], graph.vertices.get(index), keys[index]);
    }

    private void updateKeys() {
        for(Node n : contain){
            int index = graph.vertices.indexOf(n);
            ArrayList<Edge> list = graph.edges.get(index);
            for(Edge e : list){
                if(contain.contains(e.toWhere())) continue;

                int i = graph.vertices.indexOf(e.toWhere());
                if(keys[i] > e.weight){
                    keys[i] = e.weight;
                    froms[i] = graph.fromWhere(e);
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
