package DS;

import java.awt.*;

public class Edge {
    private final Node to;
    public int weight;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;

    public Edge(Node to, int weight, int startX,int startY, int endX, int endY){
        this.to = to;
        this.weight = weight;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void render(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(startX, startY, endX, endY);
        g.drawOval(startX - 2, startY - 2, 4, 4);
        g.fillOval(endX - 2, endY - 2, 4, 4);
    }

    public Node toWhere(){
        return to;
    }

    public boolean equals(Edge edge){
        return to.equals(edge.to) && weight == edge.weight;
    }
}
