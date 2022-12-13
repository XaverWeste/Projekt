package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.model.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Taskfield extends Interactable{

    private ArrayList<Task> t=new ArrayList<>();
    private Task selected;

    public Taskfield(Task...task) {
        t.add(new Task(-1,"","",Task.TaskStatus.unknown,-1));
        t.addAll(Arrays.asList(task));
        x=10;
        y=150;
        height=20;
        width=200;
    }

    public void draw(DrawTool d){
        if(t.size()>0){
            d.setCurrentColor(Color.GRAY);
            for (int i = 0; i < t.size(); i++) {
                d.drawFilledRectangle(x, y + 30 * i, width, height);
            }
            d.setCurrentColor(Color.BLACK);
            for (int i = 0; i < t.size(); i++) {
                d.drawRectangle(x, y + 30 * i, width, height);
                d.drawText(x + 5, y + height + 30 * i - 5, t.get(i).getName());
            }
        }
    }

    public void clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width){
            int i=(int) ((y-this.y)/30);
            if(i<=t.size()) selected=t.get(i);
            System.out.println(i);
        }
    }

    public void setTasks(Task...task){
        t=new ArrayList<>();
        t.add(new Task(-1,"","",Task.TaskStatus.unknown,-1));
        t.addAll(Arrays.asList(task));
        if(t.size()>0) selected=t.get(0);
    }

}
