package Algorithms;

import DS.Node;
import GUI.App;

import java.util.ArrayList;

public interface Algorithm {

    ArrayList<App> ref = new ArrayList<>();
    ArrayList<Command> commandList = new ArrayList<>();
    ArrayList<Note> noteList = new ArrayList<>();

    void run(Node start);

    void save();

    void show(Node node1, Node node2);
}
