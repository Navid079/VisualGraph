package GUI;

import Control.Const;
import Control.MouseInput;
import DS.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class App {
    private final JFrame frame;
    private Graph graph = new Graph();

    public App(JFrame frame) {
        this.frame = frame;
        frame.getComponent(0).addMouseListener(new MouseInput(this));
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && !graph.hasNode(e.getX() - Const.RIGHT, e.getY())) {
                graph.addNode(e.getX() - Const.RIGHT, e.getY());
                render();
                System.out.println("added");
            }
        } else if(e.getButton() == MouseEvent.BUTTON3){
            if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                graph.removeNode(graph.getNode(e.getX() - Const.RIGHT, e.getY()));
                render();
                System.out.println("removed");
            }
        }
    }

    private void render() {
        Graphics g = frame.getComponent(0).getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(Const.LEFT, 0, Const.WIDTH, Const.HEIGHT);

        graph.render(g);
    }
}
