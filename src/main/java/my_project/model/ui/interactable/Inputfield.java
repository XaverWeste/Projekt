package my_project.model.ui.interactable;

import KAGO_framework.view.DrawTool;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.control.ProgramController;

public class Inputfield extends Interactable {

    public class StringRow {

        private AffineTransform affinetransform;
        private FontRenderContext frc;
        private Font font;
        private String string;

        public StringRow(String string){
            this.string = string;

            affinetransform = new AffineTransform();
            frc = new FontRenderContext(affinetransform,true,true);
            font = new Font("Serif", Font.PLAIN, 15);
        }

        public void addChar(char c){
            string += c;
        }

        public double getSize(){
            return font.getStringBounds(string,frc).getWidth();
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }

    private final List<StringRow> stringList = new List();
    private final String t;
    private double maxWidth, maxHeight, minWidth, currentHeight, currentWidth;
    private boolean maxWidthReached = false, maxHeightReached = false, adjust = false, passField=false;

    public Inputfield(double x, double y, double minWidth, String text, double maxWidth, double maxHeight, ProgramController pc){
        super(pc);
        this.x=x;
        this.y=y;
        width = this.minWidth = minWidth;
        height=20;
        t=text;
        if(t.equals("password")) passField=true;
        this.maxWidth =maxWidth;
        this.maxHeight = maxHeight;
        if(maxHeight < 5) maxHeightReached = true;
        adjust = true;
    }

    public Inputfield(double x,double y,double w,double h,String text, ProgramController pc){
        super(pc);
        this.x=x;
        this.y=y;
        width=w;
        height=h;
        t=text;
        if(t.equals("password")) passField=true;
        maxHeight = 0;
        maxHeightReached = true;
        maxWidth = width;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(pc.getTheme().getLine());
        stringList.toFirst();
        if(maxWidthReached && stringList.hasAccess()){
            int i = -1;
            while(stringList.hasAccess()){
                i++;
                stringList.next();
            }
            currentWidth = maxWidth;
            currentHeight = height+i*15;
            if(height+i*15 > maxHeight) maxHeightReached = true;
        }else{
            currentWidth = width;
            currentHeight = height;
        }
        d.drawRectangle(x,y,currentWidth, currentHeight);
        d.setCurrentColor(pc.getTheme().getButton());
        d.drawFilledRectangle(x,y,currentWidth, currentHeight);
        if(stringList.isEmpty()){
            d.setCurrentColor(pc.getTheme().getBgText());
            d.drawText(x+5,y+height-5,t);
        }else{
            d.setCurrentColor(pc.getTheme().getText());
            stringList.toFirst();
            for(int i=0; stringList.hasAccess();i++){
                if(passField) d.drawText(x+5,y+height-5+i*15,getSecured(stringList.getContent()));
                else d.drawText(x+5,y+height-5+i*15,stringList.getContent().getString());
                stringList.next();
            }

        }
    }

    public void highlight(DrawTool d){
        d.setCurrentColor(pc.getTheme().getMark());
        d.drawRectangle(x,y,currentWidth, currentHeight);
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
                if(adjust) {
                    stringList.toFirst();
                    if (stringList.getContent().getSize() > minWidth) {
                        width = stringList.getContent().getSize();
                    } else {
                        width = minWidth;
                    }
                }
            }
        }else{
            stringList.append(new StringRow(""+c));
        }
    }

    public void setStringList(String s){
        stringList.toFirst();
        while(!stringList.isEmpty()) stringList.remove();
        if(s!=null){
            char[] chars = s.toCharArray();
            for (char c : chars) add(c);
        }
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

    public String getSecured(StringRow s){
        StringBuilder sb=new StringBuilder();
        char[] c=s.getString().toCharArray();
        for(int j=0;j<c.length;j++) sb.append("*");
        return sb.toString();
    }
}
