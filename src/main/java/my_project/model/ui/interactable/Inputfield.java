package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Inputfield extends Interactable {

    private String s="",t;
    private final double errorX,errorY;
    private boolean showError = false;

    public Inputfield(double x,double y,double w,double h,String text,double errorX, double errorY){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        t=text;
        this.errorX = errorX;
        this.errorY = errorY;
    }

    public Inputfield(double x,double y,double w,double h,String text){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        t=text;
        this.errorX = x+5;
        this.errorY = y+height-5;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(Color.GRAY);
        d.drawFilledRectangle(x,y,width,height);
        d.setCurrentColor(Color.BLACK);
        d.drawRectangle(x,y,width,height);
        if(s.equals("")){
            d.setCurrentColor(Color.DARK_GRAY);
            d.drawText(x+5,y+height-5,t);
        }else{
            d.drawText(x+5,y+height-5,s);
        }
        if(showError){
            d.setCurrentColor(Color.RED);
            d.drawText(errorX,errorY,"You can only use up to 18 characters!");
        }
    }

    public void clear(){
        s="";
        showError = false;
    }

    public void add(char c){
        if(s.toCharArray().length < 18){
            s+=c;
        }else{
            showError = true;
        }

    }

    public void setText(String t){
        this.s=t;
    }

    public void clearLast(){
        char[] c=s.toCharArray();
        clear();
        showError = false;
        for(int i=0;i<c.length-1;i++) add(c[i]);
    }

    public boolean clickOn(double x,double y){
        return x > this.x && y > this.y && x < this.x + width && y < this.y + height;
    }

    public String getContent(){
        return s;
    }
}
