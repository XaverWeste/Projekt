package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

import java.awt.*;

public class Button extends Interactable {

    public interface OnClick {
        void execute();
    }

    private final String s;
    private final OnClick oc;

    public Button(double x, double y, double w, double h, String t, ProgramController pc, OnClick oc){
        super(pc);
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        s=t;
        this.oc=oc;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(pc.getTheme().getButton());
        d.drawFilledRectangle(x,y,width,height);
        d.setCurrentColor(pc.getTheme().getLine());
        d.drawRectangle(x,y,width,height);
        d.setCurrentColor(pc.getTheme().getText());
        d.drawText(x+5,y+height-5,s);
    }

    public boolean clickOn(double x,double y){
        boolean b=x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height;
        if(b) oc.execute();
        return b;
    }
}
