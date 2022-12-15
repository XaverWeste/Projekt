package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.ui.interactable.Button;

public class StartScreen extends Screen{

    public StartScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        interactables.add(new Button(Config.WINDOW_WIDTH / 2 - 100, Config.WINDOW_HEIGHT / 2 - 30, 200, 20, "log in",pc, () -> pc.showScene(1)));
        interactables.add(new Button(Config.WINDOW_WIDTH / 2 - 100, Config.WINDOW_HEIGHT / 2 + 50, 200, 20, "sign up",pc, () -> pc.showScene(2)));
    }
}
