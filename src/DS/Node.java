package DS;

import Control.Const;

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
}
