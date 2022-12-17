package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

import java.awt.*;

public class Combobox extends Interactable{

    public interface OnClick{
        void execute();
    }

    private String[] options;
    private String current="";
    private final String text;
    private boolean ismarked=false;
    private final OnClick oc;

    public Combobox(double x, double y, double w, double h, String t, OnClick oc, ProgramController pc,String...o){
        super(pc);
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        text=t;
        this.oc=oc;
        options=o;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(pc.getTheme().getButton());
        d.drawFilledRectangle(x,y,width,height);
        d.setCurrentColor(pc.getTheme().getLine());
        d.drawRectangle(x,y,width,height);
        d.setCurrentColor(pc.getTheme().getText());
        if(current.equals("")) d.drawText(x+5,y+height-5,text);
        else d.drawText(x+5, y+height-5, current);
    }

    public void drawOptions(DrawTool d){
        if(ismarked){
            d.setCurrentColor(pc.getTheme().getButton());
            d.drawFilledRectangle(x,y+height,width,20*options.length);
            d.setCurrentColor(pc.getTheme().getLine());
            d.drawRectangle(x,y+height,width,20*options.length);
            d.setCurrentColor(pc.getTheme().getText());
            for(int i=0;i< options.length;i++) d.drawText(x+5,y+height*2+20*i-5,options[i]);
        }
    }

    public boolean clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height+20*options.length){
            if(!ismarked&&y<this.y+height) ismarked=true;
            else if(y<this.y+height) ismarked=false;
            else if(this.y+height<y&&ismarked){
                current=options[(int) ((y-this.y-height)/20)];
                ismarked=false;
                oc.execute();
            }
            if(ismarked) return true;
            else return y<this.y+height;
        }else{
            ismarked=false;
            return false;
        }
    }

    public void setCurrent(String s){
        current=s;
    }

    public String getSelected(){
        return current;
    }

    public void updateOptions(String...options){
        this.options=options;
    }
}
