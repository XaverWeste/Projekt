package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.ui.interactable.*;

public class ProjektScreen extends Screen{

    public ProjektScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        Projekt p=pc.getUser().getProjekt();
        interactables.add(new TextField(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID()));
        interactables.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,"deadline","status","name"));
    }

    private void sortBy(){
        Interactable i= interactables.get(1);
        if(i instanceof Combobox){
            switch(((Combobox) i).getSelected()){
                case "status" -> setUpTasks(pc.getTasks("Status"));
                case "deadline" -> setUpTasks(pc.getTasks("Deadline"));
                case "name" -> setUpTasks(pc.getTasks("Name"));
            }
        }
    }

    private void setUpTasks(Task[] tasks){
        interactables.clear();
        setUp();
        int i=0;
        for(Task t:tasks){
            interactables.add(new Taskfield(50,230+30*i,100,20,t));
            i++;
        }
    }
}
