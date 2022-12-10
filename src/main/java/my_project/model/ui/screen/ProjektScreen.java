package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.ui.interactable.Combobox;
import my_project.model.ui.interactable.Text;

public class ProjektScreen extends Screen{

    public ProjektScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        Projekt p=pc.getUser().getProjekt();
        interactables.add(new Text(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID()));
        interactables.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,"deadline","status","name"));
    }

    private void sortBy(){

    }
}
