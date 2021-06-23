package DS;

import java.util.ArrayList;

public class Graph {
    private final ArrayList<Node> vertices = new ArrayList<>();
    private final ArrayList<ArrayList<Edge>> edges = new ArrayList<>();

    public void addNode(int x, int y){
        vertices.add(new Node(x, y));
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

    public void addEdge(Node from, Node to, int weight){
        int i = vertices.indexOf(from);
        edges.get(i).add(new Edge(to, weight));
    }

    public Edge getEdge(Node from, Node to, int weight){
        Edge instance = new Edge(to, weight);
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
}
