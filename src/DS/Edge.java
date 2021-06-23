package DS;

public class Edge {
    private final Node to;
    public int weight;

    public Edge(Node to, int weight){
        this.to = to;
        this.weight = weight;
    }

    public Node toWhere(){
        return to;
    }

    public boolean equals(Edge edge){
        return to.equals(edge.to) && weight == edge.weight;
    }
}
