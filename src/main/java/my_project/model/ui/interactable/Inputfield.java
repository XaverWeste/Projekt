package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

import java.awt.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import KAGO_framework.model.abitur.datenstrukturen.List;

public class Inputfield extends Interactable {

    private List<String> stringList = new List();
    private String s="",t;
    private double errorX,errorY, maxWidth, maxHeight;
    private boolean showError = false, resize = false, maxWidthReached = false;
    double minWidth;
    AffineTransform affinetransform;
    FontRenderContext frc;
    Font font;

    public Inputfield(double x,double y,double w,double h,String text,double errorX, double errorY){
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        t=text;
        this.errorX = errorX;
        this.errorY = errorY;

        affinetransform = new AffineTransform();
        frc = new FontRenderContext(affinetransform,true,true);
        font = new Font("Tahoma", Font.PLAIN, 12);

    }

    public Inputfield(double x, double y, double w, double h, String text, boolean resize, double maxWidth, double maxHeight){
        this.x=x;
        this.y=y;
        width = minWidth = w;
        height=h;
        t=text;
        this.resize=resize;
        this.maxWidth =maxWidth;
        stringList.append("");
        stringList.toFirst();
        affinetransform = new AffineTransform();
        frc = new FontRenderContext(affinetransform,true,true);
        font = new Font("Tahoma", Font.PLAIN, 12);

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
        if(resize){
            resizeInputfield();
        }
        d.setCurrentColor(Color.BLACK);
        if(maxWidthReached){
            d.drawRectangle(x,y,maxWidth,height);
            d.setCurrentColor(Color.GRAY);
            d.drawFilledRectangle(x,y,maxWidth,height);
        }else{
            d.drawRectangle(x,y,width,height);
            d.setCurrentColor(Color.GRAY);
            d.drawFilledRectangle(x,y,width,height);
        }
        d.setCurrentColor(Color.BLACK);
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
        //if(s.toCharArray().length < 18){
            s+=c;
            if(resize && stringList.hasAccess()){
                //stringList.setContent(stringList.getContent() += c);
            }
       // }else{
       //     showError = true;
       // }

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

    public void resizeInputfield() {
        if(minWidth < (int)(font.getStringBounds(s, frc).getWidth())-40) {
            //minWidth = (int) (font.getStringBounds(s, frc).getWidth())-40;
            if(width >= maxWidth && !maxWidthReached){
                height+=14;
                s+='\n';
                width = 5;
                maxWidthReached=false;
            }
        }else {
            width = minWidth;
        }
    }

    public String getContent(){
        return s;
    }
}
