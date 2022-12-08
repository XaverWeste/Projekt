package my_project.view;


import KAGO_framework.model.InteractiveGraphicalObject;
import my_project.model.ui.screen.Screen;

import java.awt.event.MouseEvent;

public class InputManager extends InteractiveGraphicalObject {

    private Screen s;

    public InputManager(Screen screen){
        s=screen;
    }

    @Override
    public void keyReleased(int key) {
        s.keyReleased(key);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        s.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

}
