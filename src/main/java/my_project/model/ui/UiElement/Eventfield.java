package my_project.model.ui.UiElement;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;
import my_project.model.Event;
import my_project.model.Task;
import my_project.model.ui.screen.ProjectScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Eventfield extends UiElement {

    private ArrayList<Event> e =new ArrayList<>();
    private final ProjectScreen screen;

    public Eventfield(ProjectScreen s, ProgramController pc, double x,Event...event) {
        super(pc);
        screen=s;
        this.e.add(new Event(-1,"new Event","",Event.EventStatus.unknown,"",""));
        this.e.addAll(Arrays.asList(event));
        this.x=x;
        y=130;
        height=20;
        width=200;
    }

    public void draw(DrawTool d){
        if(e.size()>0){
            d.setCurrentColor(pc.getTheme().getButton());
            for (int i = 0; i < e.size(); i++) {
                d.drawFilledRectangle(x, y + 30 * i, width, height);
            }
            d.setCurrentColor(pc.getTheme().getLine());
            for (int i = 0; i < e.size(); i++) {
                d.drawRectangle(x, y + 30 * i, width, height);
                d.drawText(x + 5, y + height + 30 * i - 5, e.get(i).getName());
            }
            for (int i = 0; i < e.size(); i++) {
                if(e.get(i).getStatus()==Event.EventStatus.asPlanned) d.setCurrentColor(Color.green);
                else if(e.get(i).getStatus()==Event.EventStatus.over) d.setCurrentColor(Color.red);
                else if(e.get(i).getStatus()==Event.EventStatus.moved) d.setCurrentColor(Color.yellow);
                else if(e.get(i).getStatus()==Event.EventStatus.unknown) d.setCurrentColor(Color.white);
                else if(e.get(i).getStatus()==Event.EventStatus.canceled) d.setCurrentColor(Color.black);
                d.drawFilledCircle(x+width-10, y + 30 * i +10, 5);
            }
        }
    }

    public boolean clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width){
            int i=(int) ((y-this.y)/30);
            if(i<e.size()) screen.setUpEvent(e.get(i));
            return true;
        }
        return false;
    }

    public void setEvents(Event...event){
        e =new ArrayList<>();
        e.add(new Event(-1,"new Event","",Event.EventStatus.unknown,"",""));
        e.addAll(Arrays.asList(event));
        if(e.size()>0) screen.setUpEvent(e.get(0));
    }
}
