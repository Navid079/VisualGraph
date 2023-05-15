package Algorithms;

import DS.Edge;
import DS.Graph;
import DS.Node;
import GUI.App;

import java.awt.*;
import java.util.ArrayList;

public class Command {

    private final Graph graph;
    private final App app;
    private String command;

    public Command(String command, Graph graph, App app){
        this.command = command;
        this.graph = graph;
        this.app = app;
    }

    public void run(){
        String[] tokens = command.split(" ");
        if(tokens[0].equals("SET")){
            if(tokens[1].equals("NODE")){
                int index = Integer.parseInt(tokens[2]);
                Node n = graph.vertices.get(index);
                if(tokens[3].equals("COLOR")){
                    Color c = getColor(tokens[4]);
                    n.setColor(c);
                } else if(tokens[3].equals("LABEL")){
                    n.setLabel(tokens[4]);
                }
            } else if(tokens[1].equals("EDGE")){
                int[] indexes = {Integer.parseInt(tokens[2].split("->")[0]), Integer.parseInt(tokens[2].split("->")[1])};
                ArrayList<Edge> list = graph.edges.get(indexes[0]);
                Edge e = null;
                for(Edge edge : list){
                    if(graph.vertices.indexOf(edge.toWhere()) == indexes[1]){
                        e = edge;
                        break;
                    }
                }

                if(tokens[3].equals("COLOR")){
                    Color c = getColor(tokens[4]);
                    assert e != null;
                    e.setColor(c);
                } else if(tokens[3].equals("WEIGHT")){
                    assert e != null;
                    e.setWeight(Integer.parseInt(tokens[4]));
                }
            }
        } else if (tokens[0].equals("RESET")){
            graph.reset();
        }
        app.render();
    }

    private Color getColor(String name) {
        switch (name) {
            case "BLACK": return Color.BLACK;
            case "BLUE": return Color.BLUE;
            case "CYAN": return Color.CYAN;
            case "DARK_GRAY": return Color.DARK_GRAY;
            case "GRAY": return Color.GRAY;
            case "GREEN": return Color.GREEN;
            case "LIGHT_GRAY": return Color.LIGHT_GRAY;
            case "MAGENTA": return Color.MAGENTA;
            case "ORANGE": return Color.ORANGE;
            case "PINK": return Color.PINK;
            case "RED": return Color.RED;
            case "WHITE": return Color.WHITE;
            case "YELLOW": return Color.YELLOW;
            default: return Color.decode(name);
        }
    }

    public String toString(){
        return command;
    }
}
