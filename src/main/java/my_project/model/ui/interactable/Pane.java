package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;
import my_project.model.ui.screen.ProjectScreen;

import java.util.ArrayList;

public class Pane extends Interactable{

    private final ArrayList<Interactable> list=new ArrayList<>();
    private final ProjectScreen p;
    private boolean active=false;

    public Pane(ProgramController pc, ProjectScreen p) {
        super(pc);
        this.p=p;
    }

    @Override
    public void draw(DrawTool d) {
        if(active) {
            for (Interactable i : list) i.draw(d);
            for (Interactable i : list) if (i instanceof Combobox) ((Combobox) i).drawOptions(d);
        }
    }

    @Override
    public boolean clickOn(double x, double y) {
        if(active) {
            for (Interactable i : list) {
                if (!(i instanceof Inputfield)) {
                    if (i.clickOn(x, y)){
                        p.setActifeField(null);
                        return true;
                    }
                } else {
                    if (i.clickOn(x, y)) {
                        p.setActifeField((Inputfield) i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setActive(boolean b){
        active=b;
    }

    public boolean isActive(){
        return active;
    }

    public void add(Interactable i){
        list.add(i);
    }

    public Interactable get(int i){
        return list.get(i);
    }
}
