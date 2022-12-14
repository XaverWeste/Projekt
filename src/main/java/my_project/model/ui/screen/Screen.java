package my_project.model.ui.screen;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.ui.interactable.*;
import my_project.model.ui.interactable.Button;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Screen extends GraphicalObject {

    protected ArrayList<Interactable> interactables=new ArrayList<>();
    protected final ProgramController pc;
    protected Inputfield activeIf=null;

    public Screen(ProgramController pc){
        this.pc = pc;
        setUp();
    }

    abstract void setUp();
    
    public void resetUp(){
        interactables=new ArrayList<>();
        activeIf=null;
        setUp();
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.DARK_GRAY);
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        for(Interactable i:interactables) i.draw(d);
        if(activeIf!=null) activeIf.makiereDich(d);
    }

    public void mouseReleased(MouseEvent e){
        for(Interactable i:interactables){
            if(i instanceof Button) ((Button) i).clickOn(e.getX(),e.getY());
            else if(i instanceof Combobox) ((Combobox) i).clickOn(e.getX(),e.getY());
            else if(i instanceof Taskfield) ((Taskfield) i).clickOn(e.getX(),e.getY());
            else if(i instanceof Inputfield) if(((Inputfield) i).clickOn(e.getX(),e.getY())) activeIf=(Inputfield)i;
        }
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
