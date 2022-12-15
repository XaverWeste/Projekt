package my_project.model.ui;

import java.awt.*;

public class Theme {

    private final Color background,line,button,text, bgText, mark;

    public Theme(Color background,Color line,Color button,Color text,Color backgroundText,Color mark){
        this.background=background;
        this.line=line;
        this.button=button;
        this.text=text;
        this.bgText =backgroundText;
        this.mark=mark;
    }

    public Color getBackground() {
        return background;
    }

    public Color getLine() {
        return line;
    }

    public Color getButton() {
        return button;
    }

    public Color getText() {
        return text;
    }

    public Color getBgText() {
        return bgText;
    }

    public Color getMark() {
        return mark;
    }
}
