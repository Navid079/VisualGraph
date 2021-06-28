package DS;

public class Coordination {
    public int x;
    public int y;

    public Coordination() {
        this.x = -1;
        this.y = -1;
    }

    public Coordination(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        x = y = -1;
    }
}
