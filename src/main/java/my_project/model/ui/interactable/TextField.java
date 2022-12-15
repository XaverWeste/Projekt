package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

import java.awt.*;

public class TextField extends Interactable{

    private String t;

    public TextField(double x, double y, String t, ProgramController pc){
        super(pc);
        this.t=t;
        this.x=x;
        this.y=y;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.BLACK);
        d.drawText(x,y,t);
    }

    public void setText(String text){
        t=text;
    }

    @Override
    public boolean clickOn(double x, double y) {
        return false;
    }
}
