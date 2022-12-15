package my_project.model.ui;

import java.awt.*;

public class Theme {

    private final Color background,line,button,text;

    public Theme(Color background,Color line,Color button,Color text){
        this.background=background;
        this.line=line;
        this.button=button;
        this.text=text;
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
}
