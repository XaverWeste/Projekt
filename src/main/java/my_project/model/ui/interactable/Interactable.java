package my_project.model.ui.interactable;

import KAGO_framework.model.GraphicalObject;
import my_project.control.ProgramController;

public abstract class Interactable extends GraphicalObject {

    protected final ProgramController pc;

    public Interactable(ProgramController pc){
        this.pc=pc;
    }

    public abstract boolean clickOn(double x,double y);
}
