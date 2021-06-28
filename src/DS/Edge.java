package DS;

import java.awt.*;

public class Edge {
    private final Node to;
    public int weight;
    private final Coordination start;
    private final Coordination end;
    public final boolean isBidirectional;

    public Edge(Node to, int weight, Coordination start, Coordination end){
        this.to = to;
        this.weight = weight;
        this.start = start;
        this.end = end;
        isBidirectional = false;
    }

    public Edge(Node to, int weight, Coordination start, Coordination end, boolean isBidirectional){
        this.to = to;
        this.weight = weight;
        this.start = start;
        this.end = end;
        this.isBidirectional = isBidirectional;
    }

    public void render(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(start.x, start.y, end.x, end.y);
        g.drawOval(start.x - 2, start.y - 2, 4, 4);
        g.fillOval(end.x - 2, end.y - 2, 4, 4);

        g.getFont().deriveFont(Font.PLAIN, 12);

        double w = g.getFontMetrics().getStringBounds(String.valueOf(weight), g).getWidth();
        double h = g.getFontMetrics().getStringBounds(String.valueOf(weight), g).getHeight();

        g.setColor(Color.PINK);
        g.fillRect((int) ((start.x + end.x)/2 - w/2), (int) ((start.y+ end.y)/2 - h/2), (int)w, (int)h);

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(weight), (int) ((start.x + end.x)/2 - w/2), (int) (((start.y+ end.y)/2) + h/2));
    }

    public Node toWhere(){
        return to;
    }

    public boolean equals(Edge edge){
        return to.equals(edge.to) && weight == edge.weight;
    }
}
