package GUI;

import Control.Const;
import Control.MouseInput;
import DS.Graph;
import DS.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class App extends JFrame{
    private Graph graph = new Graph();
    private Node node1;
    private Node node2;
    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private int type = Const.ADD_NODE;

    public void init() {
        getComponent(0).addMouseListener(new MouseInput(this));
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getX() < Const.RIGHT){
            if(e.getButton() == MouseEvent.BUTTON1) {
                type = Const.ADD_NODE;
                System.out.println("add node type");
            }
            else if(e.getButton() == MouseEvent.BUTTON3){
                type = Const.ADD_EDGE;
                System.out.println("add edge type");
            }
        }
        if(e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if(type == Const.ADD_EDGE) {
                    if (graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                        if (node1 == null){
                            node1 = graph.getNode(e.getX() - Const.RIGHT, e.getY());
                            System.out.println("Node 1 selected");
                        }
                        else if (node2 == null){
                            node2 = graph.getNode(e.getX() - Const.RIGHT, e.getY());
                            System.out.println("Node 2 selected");
                        }
                        else{
                            node1 = node2 = null;
                            System.out.println("No more than two nodes");
                        }
                        if (node1 == node2){
                            node1 = node2 = null;
                            System.out.println("Not same nodes");
                        }
                    }
                } else if(type == Const.ADD_NODE){
                    if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && !graph.hasNode(e.getX() - Const.RIGHT, e.getY())) {
                        graph.addNode(e.getX() - Const.RIGHT, e.getY());
                        render();
                        System.out.println("added");
                    }
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if(type == Const.ADD_EDGE) {
                    if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && !graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                        if (node2 == null){
                            System.out.println("Select two nodes first");
                            return;
                        }
                        if (startX == -1) {
                            if(!node1.isNearMe(e.getX() - Const.RIGHT, e.getY(), 10)){
                                System.out.println("Not near start node");
                                return;
                            }
                            startX = e.getX();
                            startY = e.getY();
                            System.out.println("Coordination 1 selected");
                        } else if (endX == -1) {
                            if(!node2.isNearMe(e.getX() - Const.RIGHT, e.getY(), 10)){
                                System.out.println("Not near end node");
                                return;
                            }
                            endX = e.getX();
                            endY = e.getY();
                            graph.addEdge(node1, node2, 1, startX, startY, endX, endY);
                            render();
                            startX = startY = endX = endY = -1;
                            node1 = node2 = null;
                            System.out.println("Edge created");
                        }
                    }
                } else if(type == Const.ADD_NODE){
                    if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                        graph.removeNode(graph.getNode(e.getX() - Const.RIGHT, e.getY()));
                        render();
                        System.out.println("removed");
                    }
                }
            }
        }
    }

    private void render() {
        Graphics g = getComponent(0).getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(Const.LEFT, 0, Const.WIDTH, Const.HEIGHT);

        graph.render(g);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        render();
    }
}
