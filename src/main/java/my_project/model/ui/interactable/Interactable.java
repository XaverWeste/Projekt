package my_project.model.ui.interactable;

import KAGO_framework.model.GraphicalObject;

public abstract class Interactable extends GraphicalObject {

    public abstract boolean clickOn(double x,double y);
}
