package my_project.model.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.interactable.Button;
import my_project.model.interactable.Inputfield;

import java.awt.*;

public class LogInScreen extends Screen{

    public LogInScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        interactables.add(new Inputfield(Config.WINDOW_WIDTH/2-200,Config.WINDOW_HEIGHT/2-30,400,20));
        interactables.add(new Inputfield(Config.WINDOW_WIDTH/2-200,Config.WINDOW_HEIGHT/2+30,400,20));
        interactables.add(new Button(Config.WINDOW_WIDTH / 2, Config.WINDOW_HEIGHT / 2 + 60, 200, 20, Color.GRAY, "Login", new Button.onClick() {
            @Override
            public void execute() {

            }
        }));
    }
}
