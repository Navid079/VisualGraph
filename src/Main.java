import Control.Const;
import GUI.App;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Graph Analysis");
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(Const.LEFT + Const.WIDTH + Const.RIGHT, Const.HEIGHT));

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        App app = new App(frame);
    }
}
