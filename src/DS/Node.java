package DS;

import Control.Const;

import java.awt.*;

public class Node {
    //node's coordination on the window.
    // Note that this coordination is exactly on the middle of the node.
    private int x;
    private int y;
    private Color color = Color.BLACK;
    private Graph graph;
    private String label = "";

    public Node(int x, int y, Graph graph){
        this.x = x;
        this.y = y;
        this.graph = graph;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x >= Const.WIDTH || x < 0)
            return;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if(y >= Const.HEIGHT || y < 0)
            return;
        this.y = y;
    }

    public boolean isMe(int x, int y){
        return Math.abs(x - this.x) < Const.NODE/2 && Math.abs(y - this.y) < Const.NODE/2;
    }

    public boolean equals(Node node){
        return  x == node.x && y == node.y;
    }

    public boolean isNearMe(int x, int y) {
        return Math.abs(x - this.x) < Const.NODE/2 + Const.SPACE && Math.abs(y - this.y) < Const.NODE/2 + Const.SPACE;
    }

    public boolean isNearMe(int x, int y, int radius) {
        return Math.abs(x - this.x) < Const.NODE/2 + radius && Math.abs(y - this.y) < Const.NODE/2 + radius;
    }

    public void render(Graphics g){
        g.setColor(color);

        int x = Const.LEFT + this.x - Const.NODE/2;
        int y = this.y - Const.NODE/2;

        g.fillRect(x, y, Const.NODE, Const.NODE);

        g.getFont().deriveFont(Font.PLAIN, 8);

        double w = g.getFontMetrics().getStringBounds(getLabel(), g).getWidth();
        double h = g.getFontMetrics().getStringBounds(getLabel(), g).getHeight();

        g.setColor(Color.PINK);
        g.fillRect((int) (x - w/2), (int) (y - h/2), (int)w, (int)h);

        g.setColor(Color.BLACK);
        g.drawString(getLabel(), (int) (x - w/2), (int) (y + h/4));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        if(label.isEmpty()){
            return String.valueOf(graph.vertices.indexOf(this));
        }
        return label;
    }
}
