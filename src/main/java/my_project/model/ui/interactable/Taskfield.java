package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.model.Task;

import java.awt.*;

public class Taskfield extends Interactable{

    private Task[] t;
    private Task selected;

    public Taskfield(Task...task) {
        t = task;
        x=10;
        y=150;
        height=20;
        width=200;
    }

    public void draw(DrawTool d){
        if(t.length>0){
            d.setCurrentColor(Color.GRAY);
            for (int i = 0; i < t.length; i++) {
                d.drawFilledRectangle(x, y + 30 * i, width, height);
            }
            d.setCurrentColor(Color.BLACK);
            for (int i = 0; i < t.length; i++) {
                d.drawRectangle(x, y + 30 * i, width, height);
                d.drawText(x + 5, y + height + 30 * i - 5, t[i].getName());
            }
        }
    }

    public void clickOn(double x,double y){
        //if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height) marked=true;
    }

    public void setTasks(Task...task){
        t=task;
    }

}
