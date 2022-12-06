package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Button extends GraphicalObject {

    public interface onClick{
        void execute();
    }

    private final String s;
    private final Color color;
    private final onClick oc;

    public Button(double x,double y,double w,double h,Color c,String t,onClick oc){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        color=c;
        s=t;
        this.oc=oc;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(color);
        d.drawFilledRectangle(x,y,width,height);
        d.setCurrentColor(Color.BLACK);
        d.drawRectangle(x,y,width,height);
        d.drawText(x+5,y-5,s);
    }

    public boolean clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height){
            oc.execute();
            return true;
        }
        return false;
    }
}
