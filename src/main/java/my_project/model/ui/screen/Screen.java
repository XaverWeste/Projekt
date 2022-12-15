package my_project.model.ui.screen;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.ui.interactable.*;
import my_project.model.ui.interactable.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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
        d.setCurrentColor(pc.getTheme().getBackground());
        d.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        for(Interactable i:interactables) i.draw(d);
        for(Interactable i:interactables) if(i instanceof Combobox) ((Combobox) i).drawOptions(d);
        if(activeIf!=null) activeIf.highlight(d);
    }

    public void mouseReleased(MouseEvent e){
        for(Interactable i:interactables){
            if(!(i instanceof Inputfield)){
                if(i.clickOn(e.getX(),e.getY())) break;
            }else{
                if(i.clickOn(e.getX(),e.getY())){
                    activeIf=(Inputfield)i;
                    break;
                }
            }
        }
    }

    public void keyReleased(int key,boolean shift){
        if(activeIf!=null){
            if (key == KeyEvent.VK_BACK_SPACE) {
                activeIf.clearLast();
            }else if((key>=48&&key<=57)||key==44||key==46){
                activeIf.add((char) key);
            }else if(key>=65&&key<=90){
                if(shift) {
                    activeIf.add((char) key);
                }else{
                    activeIf.add((char) (key+32));
                }
            }else JOptionPane.showMessageDialog(null,"ungültige eingabe");
        }
    }
}
