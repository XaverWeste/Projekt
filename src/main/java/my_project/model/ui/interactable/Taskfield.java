package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.model.Task;

import java.awt.*;

public class Taskfield extends Interactable{

    private final Task t;
    private boolean marked;

    public Taskfield(double x,double y,double w,double h,Task task) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        t = task;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.GRAY);
        d.drawFilledRectangle(x,y,width,height);
        d.setCurrentColor(Color.BLACK);
        d.drawRectangle(x,y,width,height);
        d.drawText(x+5,y+height-5,t.getName());
    }

    public void clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height) marked=true;
        else marked=false;
    }

    public void setXY(double x,double y){
        this.x=x;
        this.y=y;
    }

}
