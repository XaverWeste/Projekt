package my_project.model.ui.UiElement;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;
import my_project.model.Task;
import my_project.model.ui.screen.ProjectScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Taskfield extends UiElement {

    private ArrayList<Task> t=new ArrayList<>();
    private final ProjectScreen screen;

    public Taskfield(ProjectScreen s, ProgramController pc, double x, Task...task) {
        super(pc);
        screen=s;
        t.add(new Task(-1,"new Task","",Task.TaskStatus.unknown,pc.getUser().getId(),""));
        t.addAll(Arrays.asList(task));
        this.x=x;
        y=130;
        height=20;
        width=200;
    }

    public void draw(DrawTool d){
        if(t.size()>0){
            d.setCurrentColor(pc.getTheme().getButton());
            for (int i = 0; i < t.size(); i++) {
                d.drawFilledRectangle(x, y + 30 * i, width, height);
            }
            for (int i = 0; i < t.size(); i++) {
                if(t.get(i).over())d.setCurrentColor(pc.getTheme().getMark());
                else d.setCurrentColor(pc.getTheme().getLine());
                d.drawRectangle(x, y + 30 * i, width, height);
                d.setCurrentColor(pc.getTheme().getText());
                d.drawText(x + 5, y + height + 30 * i - 5, t.get(i).getName());
            }
            d.setCurrentColor(Color.green);
            for (int i = 0; i < t.size(); i++) if(t.get(i).getStatus()==Task.TaskStatus.finished) d.drawFilledCircle(x+width-10, y + 30 * i +10, 5);
            d.setCurrentColor(Color.red);
            for (int i = 0; i < t.size(); i++) if(t.get(i).getStatus()==Task.TaskStatus.notStartedYet) d.drawFilledCircle(x+width-10, y + 30 * i +10, 5);
            d.setCurrentColor(Color.yellow);
            for (int i = 0; i < t.size(); i++) if(t.get(i).getStatus()==Task.TaskStatus.workingOn) d.drawFilledCircle(x+width-10, y + 30 * i +10, 5);
            d.setCurrentColor(Color.white);
            for (int i = 0; i < t.size(); i++) if(t.get(i).getStatus()==Task.TaskStatus.unknown) d.drawFilledCircle(x+width-10, y + 30 * i +10, 5);
            d.setCurrentColor(Color.black);
            for (int i = 0; i < t.size(); i++) if(t.get(i).getStatus()==Task.TaskStatus.canceled) d.drawFilledCircle(x+width-10, y + 30 * i +10, 5);
        }
    }

    public boolean clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width){
            int i=(int) ((y-this.y)/30);
            if(i<=t.size()) screen.setUpTask(t.get(i));
            return true;
        }
        return false;
    }

    public void setTasks(Task...task){
        t=new ArrayList<>();
        t.add(new Task(-1,"new Task","",Task.TaskStatus.unknown,pc.getUser().getId(),""));
        t.addAll(Arrays.asList(task));
        if(t.size()>0) screen.setUpTask(t.get(0));
    }

}
