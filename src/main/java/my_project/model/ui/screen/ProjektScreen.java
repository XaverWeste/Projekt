package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.ui.interactable.*;

public class ProjektScreen extends Screen{

    private Taskfield t;

    public ProjektScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        Projekt p=pc.getUser().getProjekt();
        interactables.add(new TextField(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID()));
        t=new Taskfield();
        t.setTasks(pc.getTasks("Name"));
        interactables.add(t);
        interactables.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,"deadline","status","name"));
    }

    private void sortBy(){
        Interactable i= interactables.get(2);
        if(i instanceof Combobox){
            switch(((Combobox) i).getSelected()){
                case "status" -> t.setTasks(pc.getTasks("Status"));
                case "deadline" -> t.setTasks(pc.getTasks("Deadline"));
                case "name" -> t.setTasks(pc.getTasks("Name"));
            }
        }
    }
}
