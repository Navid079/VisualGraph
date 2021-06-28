package GUI;

import Control.Const;
import Control.MouseInput;
import DS.Coordination;
import DS.Graph;
import DS.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class App extends JFrame{
    private Graph graph = new Graph();
    private Node node1;
    private Node node2;
    private Coordination startC = new Coordination();
    private Coordination endC = new Coordination();
    private int type = Const.ADD_NODE;
    private boolean bi = false;

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
                if (type == Const.ADD_NODE) {
                    type = Const.ADD_EDGE;
                    System.out.println("add edge type");
                } else {
                    bi = !bi;
                    System.out.println("bi changed");
                }
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
                        if (startC.x == -1) {
                            if(!node1.isNearMe(e.getX() - Const.RIGHT, e.getY(), 10)){
                                System.out.println("Not near start node");
                                return;
                            }
                            startC.x = e.getX();
                            startC.y = e.getY();
                            System.out.println("Coordination 1 selected");
                        } else if (endC.x == -1) {
                            if(!node2.isNearMe(e.getX() - Const.RIGHT, e.getY(), 10)){
                                System.out.println("Not near end node");
                                return;
                            }
                            endC.x = e.getX();
                            endC.y = e.getY();
                            graph.addEdge(node1, node2, 1, startC, endC, bi);
                            render();
                            startC.reset();
                            endC.reset();
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
