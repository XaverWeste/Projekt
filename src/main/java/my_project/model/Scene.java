package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Scene extends GraphicalObject {

    private final ArrayList<Interactable> interactables=new ArrayList<>();

    public Scene(){
        setUp();
    }

    abstract void setUp();

    public void draw(DrawTool d){
        d.setCurrentColor(Color.DARK_GRAY);
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
    }

    public void mouseReleased(MouseEvent e){
    }
}
