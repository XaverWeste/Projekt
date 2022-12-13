package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Button extends Interactable {

    public interface OnClick {
        void execute();
    }

    private final String s;
    private final OnClick oc;

    public Button(double x, double y, double w, double h, String t, OnClick oc){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        s=t;
        this.oc=oc;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.GRAY);
        d.drawFilledRectangle(x,y,width,height);
        d.setCurrentColor(Color.BLACK);
        d.drawRectangle(x,y,width,height);
        d.drawText(x+5,y+height-5,s);
    }

    public void clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height) oc.execute();
    }
}
