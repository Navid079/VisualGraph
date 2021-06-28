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
    private final Graph graph = new Graph();
    private Node node1;
    private Node node2;
    private final Coordination startC = new Coordination();
    private final Coordination endC = new Coordination();
    private int type = Const.ADD_NODE;
    private boolean bi = false;
    private final JPanel panel;

    private JButton typeButton;
    private JButton biButton;
    private JTextField wText;

    public App(JPanel panel){
        this.panel = panel;
    }

    public void init() {
        panel.setLayout(null);
        panel.addMouseListener(new MouseInput(this));

        typeButton = new JButton("Add Node");
        JLabel biLabel = new JLabel("Bidirectional mode:");
        JLabel wLabel = new JLabel("Weight");
        biButton = new JButton("OFF");
        wText = new JTextField();

        panel.add(typeButton);
        panel.add(biLabel);
        panel.add(wLabel);
        panel.add(biButton);
        panel.add(wText);

        typeButton.setLocation(20, 20);
        typeButton.setSize(Const.LEFT - 40, 40);
        typeButton.addActionListener(e -> {
            if(type == Const.ADD_NODE){
                type = Const.ADD_EDGE;
                typeButton.setText("Add Edge");
                biButton.setEnabled(true);
                wText.setEnabled(true);
            } else {
                type = Const.ADD_NODE;
                typeButton.setText("Add Node");
                biButton.setEnabled(false);
                wText.setEnabled(false);
            }
        });

        biLabel.setLocation(20, 70);
        biLabel.setFont(new Font("Arial", Font.PLAIN, 8));
        biLabel.setSize((Const.LEFT - 40)/2 - 5, 15);

        wLabel.setLocation((Const.LEFT - 40)/2 + 25, 70);
        wLabel.setFont(new Font("Arial", Font.PLAIN, 8));
        wLabel.setSize((Const.LEFT - 40)/2 - 5, 15);

        biButton.setLocation(20, 90);
        biButton.setSize((Const.LEFT - 40)/2 - 5, 40);
        biButton.addActionListener(e -> {
            bi = !bi;
            if(bi){
                biButton.setText("ON");
            } else{
                biButton.setText("OFF");
            }
        });
        biButton.setEnabled(false);

        wText.setLocation((Const.LEFT - 40)/2 + 25, 90);
        wText.setSize((Const.LEFT - 40)/2 - 5, 40);
        wText.setEnabled(false);
    }

    public void mouseClicked(MouseEvent e) {
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
                            int w;
                            try{
                                w = Integer.parseInt(wText.getText());
                            } catch(Exception ex){
                                w = 1;
                            }
                            graph.addEdge(node1, node2, w, startC, endC, bi);
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
        Graphics g = panel.getGraphics();

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
