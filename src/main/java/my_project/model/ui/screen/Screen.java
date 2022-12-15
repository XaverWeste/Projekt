package my_project.model.ui.screen;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.ui.interactable.*;
import my_project.model.ui.interactable.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Screen extends GraphicalObject {

    protected ArrayList<Interactable> interactables=new ArrayList<>();
    protected final ProgramController pc;
    protected Inputfield activeIf=null;
    private int[] allowedNumber;
    private int[] allowedLetter;

    public Screen(ProgramController pc){
        allowedNumber = new int[]{48,49,50,51,52,53,54,55,56,57,}; //Zahlen
        allowedLetter = new int[]{ 65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90, //Buchstaben-Groß
                97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122}; //Buchstaben-Klein
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

    public void keyPressed(int key,boolean shift) {

    }

    public void keyReleased(int key,boolean shift){
        if(activeIf!=null){
            if (key == KeyEvent.VK_BACK_SPACE) {
                activeIf.clearLast(); //Delete
            }
            for(int i = 0; i < allowedNumber.length-1; i++){
                if(key == allowedNumber[i]){
                    activeIf.add((char) key);
                }
            }
            for(int i = 0; i < allowedLetter.length-1; i++){
                if(key == allowedLetter[i]){
                    if(shift) {
                        activeIf.add((char) key);
                    }else{
                        activeIf.add((char) (key+32));
                    }
                }
            }
        }
    }
}
