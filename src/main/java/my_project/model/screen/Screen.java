package my_project.model.screen;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.interactable.Inputfield;
import my_project.model.interactable.Interactable;
import my_project.model.interactable.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Screen extends GraphicalObject {

    protected final ArrayList<Interactable> interactables=new ArrayList<>();
    protected final ProgramController pc;
    protected Inputfield activeIf=null;

    public Screen(ProgramController pc){
        this.pc = pc;
        setUp();
    }

    abstract void setUp();

    public void draw(DrawTool d){
        d.setCurrentColor(Color.DARK_GRAY);
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        for(Interactable i:interactables) i.draw(d);
    }

    public void mouseReleased(MouseEvent e){
        for(Interactable i:interactables) if(i instanceof Button) ((Button) i).clickOn(e.getX(),e.getY());
        for(Interactable i:interactables) if(i instanceof Inputfield) ((Inputfield) i).clickOn(e.getX(),e.getY());
    }

    public void keyReleased(int key) {
        if(activeIf!=null){
            switch(key){
                case 8 -> activeIf.clearLast();
                case 10 -> activeIf=null;
                default -> activeIf.add((char)key);
            }
        }
    }
}
