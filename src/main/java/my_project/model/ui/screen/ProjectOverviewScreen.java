package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.ui.interactable.Button;

public class ProjectOverviewScreen extends Screen{

    public ProjectOverviewScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        int i=1;
        /*
        for(Projekt p:pc.getProjekts()){

            new Button((Config.WINDOW_WIDTH / 5 - 40) * (i % 5), (Config.WINDOW_HEIGHT / 8 - 40) * (i / 5), 200, 50, p.getName(), () -> selectProjekt(p.getProjektID()));
            i++;
        }

         */
    }

    public void selectProjekt(int projectId){
        //TODO
    }
}
