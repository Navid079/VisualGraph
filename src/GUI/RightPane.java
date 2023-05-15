package GUI;

import Algorithms.Note;
import Control.Const;
import DS.Edge;
import DS.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RightPane {

    private ArrayList<JComponent> comp = new ArrayList<>();

    private final App app;
    private Node node;

    public RightPane(App app) {
        this.app = app;
    }

    public void setNode(Node node) {
        if(this.node != null)
            this.node.setColor(Color.BLACK);
        if(node == this.node)
            node = null;
        this.node = node;
    }

    public void render(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(Const.LEFT + Const.WIDTH, 0, Const.RIGHT, Const.HEIGHT);

        for(JComponent c : comp){
            app.panel.remove(c);
        }
        comp.clear();

        if(app.type == Const.EDIT_NODE){
            if(node == null)
                return;
            ArrayList<Edge> list = app.graph.edges.get(app.graph.vertices.indexOf(node));
            int i = 0;

            g.fillRect(Const.LEFT + Const.WIDTH, 0, Const.RIGHT, Const.HEIGHT);

            for(Edge e : list){
                int x = Const.LEFT + Const.WIDTH + 20;
                int y = i*45 + 5;
                g.setColor(Color.DARK_GRAY);
                g.fillRect(x, y, Const.RIGHT - 40, 40);

                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.setColor(Color.WHITE);
                String str = "To: " + e.toWhere().getLabel() + " | Weight: " + e.weight;
                g.drawString(str, x+5, y+20);

                JButton b = new JButton("delete");
                comp.add(b);
                app.panel.add(b);
                b.setLocation(x + Const.RIGHT - 60, y + 5);
                b.setSize(15, 30);
                b.addActionListener(event -> {
                    list.remove(e);
                    if(e.isBidirectional){
                        Node fr = e.toWhere();
                        app.graph.removeEdge(fr, node, e.weight);
                    }
                    app.panel.remove(b);
                    app.render();
                });
                i++;
            }
        } else if(app.type == Const.ALGORITHM_RUN){
            JComboBox<String> stepChoose = new JComboBox<>();
            stepChoose.addItem("--Choose Step--");
            for(Note n : app.algorithm.noteList){
                stepChoose.addItem(n.note);
            }
            app.panel.add(stepChoose);
            comp.add(stepChoose);
            stepChoose.setLocation(Const.LEFT + Const.WIDTH + 20, 20);
            stepChoose.setSize(Const.RIGHT - 40, 20);
            stepChoose.addActionListener(e -> {
                if(stepChoose.getSelectedItem().equals("--Choose Step--"))
                    return;
                int i = stepChoose.getSelectedIndex() - 1;
                app.algorithm.noteList.get(i).run();
            });
        }
    }
}
