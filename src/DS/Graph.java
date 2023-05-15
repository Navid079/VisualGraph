package DS;

import Control.Const;
import GUI.App;

import java.awt.*;
import java.util.ArrayList;

public class Graph {
    public final ArrayList<Node> vertices = new ArrayList<>();
    public final ArrayList<ArrayList<Edge>> edges = new ArrayList<>();
    private App app;

    public Graph(App app){
        this.app = app;
    }

    public void addNode(int x, int y){
        vertices.add(new Node(x, y, this));
        edges.add(new ArrayList<>());
    }

    public Node getNode(int x, int y){
        for(Node n : vertices){
            if(n.isMe(x, y))
                return n;
        }
        return null;
    }

    public void removeNode(Node n){
        int i = vertices.indexOf(n);
        vertices.remove(i);
        edges.remove(i);
    }

    public void addEdge(Node from, Node to, int weight, Coordination start, Coordination end, boolean bi){

        int i = vertices.indexOf(from);
        Edge e = new Edge(to, weight, new Coordination(start.x, start.y), new Coordination(end.x, end.y));
        edges.get(i).add(e);
        if(bi) {
            i = vertices.indexOf(to);
            edges.get(i).add(new Edge(from, weight, new Coordination(end.x, end.y), new Coordination(start.x, start.y), e));
        }
    }

    public Edge getEdge(Node from, Node to, int weight){
        Edge instance = new Edge(to, weight, new Coordination(), new Coordination());
        ArrayList<Edge> list = edges.get(vertices.indexOf(from));
        for(Edge edge : list){
            if(edge.equals(instance)){
                return edge;
            }
        }
        return null;
    }

    public void removeEdge(Node from, Node to, int weight){
        Edge edge = getEdge(from, to, weight);
        edges.get(vertices.indexOf(from)).remove(edge);
    }

    public void render(Graphics g) {
        for(Node node : vertices){
            node.render(g);
        }

        for(ArrayList<Edge> list : edges){
            for(Edge edge : list)
                edge.render(g);
        }
    }

    public boolean hasNode(int x, int y){
        for(Node n : vertices){
            if(n.isNearMe(x, y))
                return true;
        }
        return false;
    }

    public boolean hasExactNode(int x, int y) {
        for(Node n : vertices){
            if(n.isMe(x, y))
                return true;
        }
        return false;
    }

    public void reset() {
        for (Node n : vertices)
            n.setColor(Color.BLACK);

        for (ArrayList<Edge> list : edges){
            for(Edge edge : list){
                edge.setColor(Color.BLACK);
            }
        }
        app.render();
    }

    public Node fromWhere(Edge e){
        for(int i = 0; i < vertices.size(); i++){
            if(edges.get(i).contains(e))
                return vertices.get(i);
        }
        return null;
    }
}
