package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Text extends Interactable{

    private final String t;

    public Text(double x,double y,String t){
        this.t=t;
        this.x=x;
        this.y=y;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.BLACK);
        d.drawText(x,y,t);
    }
}
