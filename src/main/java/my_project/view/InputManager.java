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
        if(key == 16){
            shift = false;
        }else s.keyReleased(key,shift);
    }

    @Override
    public void keyPressed(int key) {
        if(key == 16) shift = true;
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
