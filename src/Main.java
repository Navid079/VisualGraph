import Control.Const;
import GUI.App;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JPanel panel = new JPanel();
        App frame = new App(panel);
        frame.setTitle("Graph Analysis");
        panel.setPreferredSize(new Dimension(Const.LEFT + Const.WIDTH + Const.RIGHT, Const.HEIGHT));

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.init();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
