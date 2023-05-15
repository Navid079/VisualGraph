package GUI;

import Algorithms.Algorithm;
import Algorithms.Command;
import Algorithms.MST.Kruskal;
import Algorithms.MST.Prime;
import Algorithms.Search.BFS;
import Algorithms.Search.DFS;
import Algorithms.ShortestPath.AllPair.FloydWarshall;
import Algorithms.ShortestPath.SingleSource.BellmanFord;
import Algorithms.ShortestPath.SingleSource.Dijkstra;
import Control.Const;
import Control.MouseInput;
import DS.Coordination;
import DS.Edge;
import DS.Graph;
import DS.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class App extends JFrame{
    public final Graph graph = new Graph(this);
    private Node node1;
    private Node node2;
    private final Coordination startC = new Coordination();
    private final Coordination endC = new Coordination();
    public int type = Const.ADD_NODE;
    private boolean bi = false;
    public final JPanel panel;
    public Algorithm algorithm;
    private int t;

    private JButton typeButton;
    private JButton biButton;
    private JTextField wText;
    private JComboBox<String> algorithmChoose;
    private JButton algorithmRunButton;
    private JButton saveButton;
    private JButton node1Button;
    private JButton node2Button;
    private JButton resultButton;

    private final RightPane rightPane = new RightPane(this);
    private boolean node1select = false;
    private boolean node2select = false;

    public App(JPanel panel){
        this.panel = panel;
    }

    public void init() {
        panel.setLayout(null);
        panel.addMouseListener(new MouseInput(this));
        panel.setBackground(Color.GRAY);

        typeButton = new JButton("Add Node");
        JLabel biLabel = new JLabel("Bidirectional mode:");
        JLabel wLabel = new JLabel("Weight");
        biButton = new JButton("OFF");
        wText = new JTextField();
        algorithmChoose = new JComboBox<>();
        algorithmRunButton = new JButton("Run");
        JButton resetButton = new JButton("Reset");
        JButton clearButton = new JButton("Clear");
        saveButton = new JButton("Save to file");
        node1Button = new JButton("Select Node 1");
        node2Button = new JButton("Select Node 2");
        resultButton = new JButton("Show Result for");

        panel.add(typeButton);
        panel.add(biLabel);
        panel.add(wLabel);
        panel.add(biButton);
        panel.add(wText);
        panel.add(algorithmChoose);
        panel.add(algorithmRunButton);
        panel.add(resetButton);
        panel.add(clearButton);
        panel.add(saveButton);
        panel.add(node1Button);
        panel.add(node2Button);
        panel.add(resultButton);

        typeButton.setLocation(20, 20);
        typeButton.setSize(Const.LEFT - 40, 40);
        typeButton.addActionListener(e -> {
            saveButton.setEnabled(false);
            if(type == Const.ADD_NODE){
                type = Const.ADD_EDGE;
                typeButton.setText("Add Edge");
                biButton.setEnabled(true);
                wText.setEnabled(true);
            } else if(type == Const.ADD_EDGE) {
                type = Const.EDIT_NODE;
                typeButton.setText("Edit Node");
                biButton.setEnabled(false);
                wText.setEnabled(false);
            } else {
                rightPane.setNode(null);
                type = Const.ADD_NODE;
                typeButton.setText("Add Node");
                render();
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

        algorithmChoose.setLocation(20, 140);
        algorithmChoose.setSize(Const.LEFT - 40, 20);
        algorithmChoose.addItem("--Select Algorithm--");
        algorithmChoose.addItem("Breadth First Search");
        algorithmChoose.addItem("Depth First Search");
        algorithmChoose.addItem("Kruskal");
        algorithmChoose.addItem("Prime");
        algorithmChoose.addItem("Bellman-Ford");
        algorithmChoose.addItem("Dijkstra");
        algorithmChoose.addItem("Floyd-Warshall");
        algorithmChoose.addActionListener(e -> {
            if(algorithmChoose.getSelectedItem().equals("--Select Algorithm--"))
                algorithmRunButton.setEnabled(false);
            else
                algorithmRunButton.setEnabled(true);
            saveButton.setEnabled(false);
        });

        algorithmRunButton.setLocation(20, 170);
        algorithmRunButton.setSize(Const.LEFT - 40, 20);
        algorithmRunButton.addActionListener(e -> {
            if(algorithmRunButton.getText().equals("Run")) {
                algorithmRunButton.setText("Finish");
                t = type;
                graph.reset();

                if (algorithmChoose.getSelectedItem().equals("Breadth First Search")) {
                    algorithm = new BFS(this, graph);
                    algorithm.run(graph.vertices.get(0));

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(false);
                } else if (algorithmChoose.getSelectedItem().equals("Depth First Search")) {
                    algorithm = new DFS(this, graph);
                    algorithm.run(graph.vertices.get(0));

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(false);
                } else if (algorithmChoose.getSelectedItem().equals("Kruskal")) {
                    algorithm = new Kruskal(this, graph);
                    algorithm.run(graph.vertices.get(0));

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(true);
                } else if (algorithmChoose.getSelectedItem().equals("Prime")) {
                    algorithm = new Prime(this, graph);
                    algorithm.run(graph.vertices.get(0));

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(true);
                } else if (algorithmChoose.getSelectedItem().equals("Bellman-Ford")) {
                    algorithm = new BellmanFord(this, graph);
                    algorithm.run(graph.vertices.get(0));

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(true);
                } else if (algorithmChoose.getSelectedItem().equals("Dijkstra")) {
                    algorithm = new Dijkstra(this, graph);
                    algorithm.run(graph.vertices.get(0));

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(true);
                } else if (algorithmChoose.getSelectedItem().equals("Floyd-Warshall")) {
                    algorithm = new FloydWarshall(this, graph);
                    algorithm.run(null);

                    for (Command c : algorithm.commandList) {
                        System.out.println(c.toString());
                    }
                    saveButton.setEnabled(true);
                }
                type = Const.ALGORITHM_RUN;
            }
            else {
                algorithmRunButton.setText("Run");
                algorithm.commandList.clear();
                type = t;
                saveButton.setEnabled(false);
                resultButton.setEnabled(false);
            }
            render();
        });
        algorithmRunButton.setEnabled(false);

        resetButton.setLocation(20, 200);
        resetButton.setSize((Const.LEFT - 40) / 2 - 5, 40);
        resetButton.addActionListener(e -> {
            graph.reset();
        });

        clearButton.setLocation((Const.LEFT - 40) / 2 + 25, 200);
        clearButton.setSize((Const.LEFT - 40) / 2 - 5, 40);
        clearButton.addActionListener(e -> {
            graph.vertices.clear();
            graph.edges.clear();
            render();
        });

        saveButton.setLocation(20, 250);
        saveButton.setSize(Const.LEFT - 40, 40);
        saveButton.addActionListener(e -> algorithm.save());
        saveButton.setEnabled(false);

        node1Button.setLocation(20, 310);
        node1Button.setSize(Const.LEFT - 40, 40);
        node1Button.addActionListener(e -> {node1select = true; node2select=false;});
        node1Button.setEnabled(true);

        node2Button.setLocation(20, 370);
        node2Button.setSize(Const.LEFT - 40, 40);
        node2Button.addActionListener(e -> {node1select = false; node2select = true;});
        node2Button.setEnabled(true);

        resultButton.setLocation(20, 430);
        resultButton.setSize(Const.LEFT - 40, 40);
        resultButton.addActionListener(e -> {
            algorithm.show(node1, node2);
        });
        resultButton.setEnabled(false);
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if(node1select) {
                    if (graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                        node1 = graph.getNode(e.getX() - Const.RIGHT, e.getY());
                        node1Button.setText("Select Node 1 (" + node1.getLabel() + ")");
                        node1select = false;
                    }
                } else if (node2select) {
                    if (graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                        node2 = graph.getNode(e.getX() - Const.RIGHT, e.getY());
                        node2Button.setText("Select Node 2 (" + node2.getLabel() + ")");
                        System.out.println("Node 2 selected");
                        node2select = false;
                    }
                } else if(type == Const.ADD_NODE){
                    if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && !graph.hasNode(e.getX() - Const.RIGHT, e.getY())) {
                        graph.addNode(e.getX() - Const.RIGHT, e.getY());
                        render();
                        System.out.println("added");
                    }
                } else if(type == Const.EDIT_NODE){
                    if (e.getX() > Const.RIGHT && e.getX() < Const.RIGHT + Const.WIDTH && graph.hasExactNode(e.getX() - Const.RIGHT, e.getY())) {
                        Node n = graph.getNode(e.getX() - Const.RIGHT, e.getY());
                        n.setColor(Color.CYAN);
                        rightPane.setNode(n);
                        render();
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

    public void render() {
        Graphics g = panel.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(Const.LEFT, 0, Const.WIDTH, Const.HEIGHT);

        graph.render(g);
        rightPane.render(g);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        render();
    }

    public void enableShow() {
        resultButton.setEnabled(true);
    }
}
