package Control;

import GUI.App;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private final App app;

    public MouseInput(App app) {
        this.app = app;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        app.mouseClicked(e);
    }
}
