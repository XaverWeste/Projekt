package my_project.model.ui.UiElement;

import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

public class TextField extends UiElement {

    private String t;

    public TextField(double x, double y, String t, ProgramController pc){
        super(pc);
        this.t=t;
        this.x=x;
        this.y=y;
    }

    public void draw(DrawTool d){
        d.setCurrentColor(pc.getTheme().getText());
        d.drawText(x,y,t);
    }

    public void setText(String text){
        t=text;
    }

    @Override
    public boolean clickOn(double x, double y) {
        return false;
    }
}
