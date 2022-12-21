package my_project.model.ui.UiElement;

import KAGO_framework.model.GraphicalObject;
import my_project.control.ProgramController;

public abstract class UiElement extends GraphicalObject {

    protected final ProgramController pc;

    public UiElement(ProgramController pc){
        this.pc=pc;
    }

    public abstract boolean clickOn(double x,double y);
}
