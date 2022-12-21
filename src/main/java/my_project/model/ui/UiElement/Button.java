package my_project.model.ui.UiElement;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

public class Button extends UiElement {

    public interface OnClick {
        void execute();
    }

    private String s;
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
        d.drawFilledArc(x+width-height,y,height,-90,180,0);
        d.drawFilledArc(x,y,height,90,180,0);
        d.drawFilledRectangle(x+height/2,y,width-height,height);
        d.setCurrentColor(pc.getTheme().getLine());
        d.drawArc(x+width-height,y,height,-90,180,0);
        d.drawArc(x,y,height,90,180,0);
        d.drawLine(x+height/2,y,x+width-height/2,y);
        d.drawLine(x+height/2,y+height,x+width-height/2,y+height);
        d.setCurrentColor(pc.getTheme().getText());
        d.drawText(x+10,y+height-5,s);
    }

    public boolean clickOn(double x,double y){
        boolean b=x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height;
        if(b) oc.execute();
        return b;
    }

    public void setText(String s){
        this.s=s;
    }
}
