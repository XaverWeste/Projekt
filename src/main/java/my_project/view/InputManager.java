package my_project.view;


import KAGO_framework.model.InteractiveGraphicalObject;
import my_project.model.ui.screen.Screen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputManager extends InteractiveGraphicalObject {

    private Screen s;
    private boolean shift = false;

    public InputManager(Screen screen){
        s=screen;
    }

    @Override
    public void keyReleased(int key) {
        if(key == KeyEvent.VK_CAPS_LOCK){
            shift = !shift;
        }
    }

    @Override
    public void keyPressed(int key) {
        s.keyPressed(key,shift);
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
