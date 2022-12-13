package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

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

    public Combobox(double x,double y,double w,double h,String t,OnClick oc,String...o){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        text=t;
        this.oc=oc;
        options=o;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.GRAY);
        d.drawFilledRectangle(x,y,width,height);
        if(ismarked) d.drawFilledRectangle(x,y+height,width,20*options.length);
        d.setCurrentColor(Color.BLACK);
        d.drawRectangle(x,y,width,height);
        if(current.equals("")) d.drawText(x+5,y+height-5,text);
        else d.drawText(x+5, y+height-5, current);
        if(ismarked){
            d.drawRectangle(x,y+height,width,20*options.length);
            for(int i=0;i< options.length;i++) d.drawText(x+5,y+height*2+20*i-5,options[i]);
        }
    }

    public void clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height+20*options.length){
            if(!ismarked&&y<this.y+height) ismarked=true;
            else if(y<this.y+height) ismarked=false;
            else if(this.y+height<y&&ismarked){
                current=options[(int) ((y-this.y-height)/20)];
                ismarked=false;
                oc.execute();
            }
        }else{
            ismarked=false;
        }
    }

    public String getSelected(){
        return current;
    }

    public void updateOptions(String...options){
        this.options=options;
    }
}
