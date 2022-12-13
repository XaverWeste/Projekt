package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

import java.awt.*;
import KAGO_framework.model.abitur.datenstrukturen.List;

public class Inputfield extends Interactable {

    private List<StringRow> stringList = new List();
    private String t;
    private double maxWidth, maxHeight;
    private boolean maxWidthReached = false, maxHeightReached = false;

    public Inputfield(double x, double y, double w, double h, String text, double maxWidth, double maxHeight){
        this.x=x;
        this.y=y;
        width = w;
        height=h;
        t=text;
        this.maxWidth =maxWidth;
        this.maxHeight = maxHeight;
        if(maxHeight < 5) maxHeightReached = true;

    }

    public Inputfield(double x,double y,double w,double h,String text){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        t=text;
        maxHeight = 0;
        maxHeightReached = true;
        maxWidth = width;
    }

    public void draw(DrawTool d){

        //Recangel
        d.setCurrentColor(Color.BLACK);
        if(maxWidthReached){
            int i = -1;
            stringList.toFirst();
            while(stringList.hasAccess()){
                i++;
                stringList.next();
            }
            if(height+i*15 > maxHeight) maxHeightReached = true;
            d.drawRectangle(x,y,maxWidth,height+i*15);
            System.out.println("i: " + i);
            d.setCurrentColor(Color.GRAY);
            d.drawFilledRectangle(x,y,maxWidth,height+i*15);
        }else{
            d.drawRectangle(x,y,width,height);
            d.setCurrentColor(Color.GRAY);
            d.drawFilledRectangle(x,y,width,height);
        }

        //Text
        d.setCurrentColor(Color.BLACK);
        if(stringList.isEmpty()){
            d.drawText(x+5,y+height-5,t);
        }else{
            stringList.toFirst();
            for(int i=0; stringList.hasAccess();i++){
                d.drawText(x+5,y+height-5+i*15,stringList.getContent().getString());
                stringList.next();
            }

        }
    }

    public void clear(){
        stringList.toFirst();
        while(stringList.hasAccess()){
            stringList.remove();
        }
    }

    public void add(char c){
        stringList.toLast();
        if(stringList.hasAccess()){
            if(stringList.getContent().getSize() >= maxWidth){
                if(!maxHeightReached){
                    stringList.append(new StringRow(""+c));
                }
                maxWidthReached = true;
            }else{
                stringList.getContent().addChar(c);
            }
        }else{
            stringList.append(new StringRow(""+c));
        }
    }

    public void setText(String t){
        stringList.toFirst();
        while(stringList.hasAccess()){
            stringList.remove();
        }
        this.t = t;
    }

    public void clearLast(){
        stringList.toLast();
        if(stringList.hasAccess() && stringList.getContent().getString().equals("")) {
            stringList.remove();
            maxHeightReached = false;
        }
        if(stringList.hasAccess()) {
            char[] c = stringList.getContent().getString().toCharArray();
            stringList.getContent().setString("");
            for (int i = 0; i < c.length - 1; i++) add(c[i]);
            if(stringList.getContent().getString().equals("")) stringList.remove();
        }
    }

    public boolean clickOn(double x,double y){
        return x > this.x && y > this.y && x < this.x + width && y < this.y + height;
    }


    public String getContent(){
        StringBuilder s = new StringBuilder();
        stringList.toFirst();
        while(stringList.hasAccess()){
            s.append(stringList.getContent().getString());
            stringList.next();
        }
        return s.toString();
    }
}
