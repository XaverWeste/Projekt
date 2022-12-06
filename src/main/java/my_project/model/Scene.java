package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.model.interactable.Inputfield;
import my_project.model.interactable.Interactable;
import my_project.model.interactable.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
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
        for(Interactable i:interactables) if(i instanceof Button) ((Button) i).clickOn(e.getX(),e.getY());
        for(Interactable i:interactables) if(i instanceof Inputfield) ((Inputfield) i).clickOn(e.getX(),e.getY());
    }

    public void keyReleased(int key) {
        for(Interactable i:interactables) if(i instanceof Inputfield){
            i=(Inputfield)i;
            if(((Inputfield) i).isActive()){
                if(KeyEvent.VK_ENTER==key){

                }else ((Inputfield) i).add((char)key);
            }
        }
    }
}
