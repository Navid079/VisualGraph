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

public class BFS implements Algorithm {

    private final Graph graph;
    private final App app;
    public ArrayList<Node> Queue = new ArrayList<>();
    public ArrayList<Node> explored = new ArrayList<>();

    public BFS(App app, Graph graph){
        ref.clear();

        ref.add(app);
        this.app = app;
        this.graph = graph;
    }

    public void run(Node start){
        Queue.clear();
        explored.clear();
        noteList.clear();
        commandList.clear();

        Queue.add(start);

        while(!Queue.isEmpty()){
            start = Queue.remove(0);
            int index = graph.vertices.indexOf(start);
            ArrayList<Edge> edges = graph.edges.get(index);

            start.setColor(Color.BLUE);
            commandList.add(new Command("SET NODE " + index + " COLOR BLUE", graph, app));
            noteList.add(new Note("Finalize node " + index, commandList.size(), this));

            app.render();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            for(Edge e : edges){
                if(explored.contains(e.toWhere()) || Queue.contains(e.toWhere())) continue;
                Queue.add(e.toWhere());
                e.toWhere().setColor(Color.CYAN);
                commandList.add(new Command("SET NODE " + graph.vertices.indexOf(e.toWhere()) + " COLOR CYAN", graph, app));

                e.setColor(Color.RED);
                commandList.add(new Command("SET EDGE " + index + "->" + graph.vertices.indexOf(e.toWhere()) + " COLOR RED", graph, app));


                noteList.add(new Note("Visit node " + graph.vertices.indexOf(e.toWhere()) + " by " + index , commandList.size(), this));
                app.render();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }

            explored.add(start);
        }
    }

    @Override
    public void save() {}

    @Override
    public void show(Node node1, Node node2) {

    }
}
