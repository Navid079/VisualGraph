package DS;

import Control.Const;

import java.awt.*;

public class Node {
    //node's coordination on the window.
    // Note that this coordination is exactly on the middle of the node.
    private int x;
    private int y;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
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
        g.setColor(Color.BLACK);

        int x = Const.LEFT + this.x - Const.NODE/2;
        int y = this.y - Const.NODE/2;

        g.drawRect(x, y, Const.NODE, Const.NODE);
    }
}
