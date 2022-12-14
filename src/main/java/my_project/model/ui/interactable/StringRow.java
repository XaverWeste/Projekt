package my_project.model.ui.interactable;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class StringRow {

    private AffineTransform affinetransform;
    private FontRenderContext frc;
    private Font font;
    private String string;

    public StringRow(String string){
        this.string = string;

        affinetransform = new AffineTransform();
        frc = new FontRenderContext(affinetransform,true,true);
        font = new Font("Serif", Font.PLAIN, 13);
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
