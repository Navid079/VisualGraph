package Algorithms;

import DS.Graph;
import GUI.App;

public class Note {

    public String note;
    public int index;
    public Algorithm algorithm;

    public Note(String note, int index, Algorithm algorithm) {
        this.note = note;
        this.index = index;
        this.algorithm = algorithm;
    }

    public void run(){
        algorithm.ref.get(0).graph.reset();
        for(int i = 0; i < index; i++){
            algorithm.commandList.get(i).run();
        }
    }
}
