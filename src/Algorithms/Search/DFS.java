package Algorithms.Search;

import Algorithms.Algorithm;
import Algorithms.Command;
import Algorithms.Note;
import DS.Edge;
import DS.Graph;
import DS.Node;
import GUI.App;

import java.awt.*;
import java.util.ArrayList;

public class DFS implements Algorithm {

    private final Graph graph;
    private final App app;
    public ArrayList<Node> Stack = new ArrayList<>();
    public ArrayList<Node> explored = new ArrayList<>();

    public DFS(App app, Graph graph){
        ref.clear();

        ref.add(app);
        this.app = app;
        this.graph = graph;
    }

    public void run(Node start){
        Stack.clear();
        explored.clear();
        noteList.clear();
        commandList.clear();

        Stack.add(start);
        start.setColor(Color.CYAN);
        commandList.add((new Command("SET NODE " + graph.vertices.indexOf(start) + " COLOR CYAN", graph, app)));
        noteList.add(new Note("Visit node " + graph.vertices.indexOf(start) + " initially", commandList.size(), this));
        app.render();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        dfsVisit(start);
    }

    private void dfsVisit(Node n){
        for(Edge x : graph.edges.get(graph.vertices.indexOf(n))){
            if(Stack.contains(x.toWhere()) || explored.contains(x.toWhere())) continue;

            x.toWhere().setColor(Color.CYAN);
            commandList.add(new Command("SET NODE " + graph.vertices.indexOf(x.toWhere()) + " COLOR CYAN", graph, app));

            x.setColor(Color.RED);
            commandList.add(new Command("SET EDGE " + graph.vertices.indexOf(n) + "->" + graph.vertices.indexOf(x.toWhere()) + " COLOR RED", graph, app));
            noteList.add(new Note("Visit node " + graph.vertices.indexOf(x.toWhere()) + " by " + graph.vertices.indexOf(n), commandList.size(), this));
            Stack.add(x.toWhere());
            app.render();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            dfsVisit(x.toWhere());
        }

        n.setColor(Color.BLUE);
        commandList.add(new Command("SET NODE " + graph.vertices.indexOf(n) + " COLOR BLUE", graph, app));
        noteList.add(new Note("Finalize node " + graph.vertices.indexOf(n), commandList.size(), this));

        explored.add(n);
        Stack.remove(n);
        app.render();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void save() {}

    @Override
    public void show(Node node1, Node node2) {

    }
}
