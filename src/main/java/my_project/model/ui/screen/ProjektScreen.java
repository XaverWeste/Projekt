package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.ui.interactable.*;

public class ProjektScreen extends Screen{

    private Taskfield t;
    private int id;

    public ProjektScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        Projekt p=pc.getUser().getProjekt();
        interactables.add(new TextField(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID()));
        t=new Taskfield(this);
        interactables.add(t);
        interactables.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,"deadline","status","name"));
        interactables.add(new Button(800, 100, 100, 20, "save",this::save));
        interactables.add(new Inputfield(300,130,450,20,"name"));
        interactables.add(new Inputfield(300,160,450,20,"deadline"));
        interactables.add(new Combobox(300, 190, 450, 20, "status", this::setStatus,"notStartedYet","workingOn","finished","canceled","unknown"));
        t.setTasks(pc.getTasks("Name"));
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

    public void setUpTask(Task t){
        id=t.getId();
        Interactable i= interactables.get(4);
        if(i instanceof Inputfield) ((Inputfield) i).setText(t.getName());
        i= interactables.get(5);
        if(i instanceof Inputfield) ((Inputfield) i).setText(t.getDeadline());
        i= interactables.get(6);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(t.getStatus().toString());
    }

    private void save(){
        Task t=new Task(id,"","", Task.TaskStatus.unknown,-1);
        Interactable i= interactables.get(4);
        if(i instanceof Inputfield) t.setName(((Inputfield) i).getContent());
        i= interactables.get(5);
        if(i instanceof Inputfield) t.setDeadline(((Inputfield) i).getContent());
        i= interactables.get(6);
        if(i instanceof Combobox) t.setStatus(pc.getStatus(((Combobox) i).getSelected()));
        if((t.getId()>-1)) pc.updateTask(t);
        else pc.createTask(t);
    }

    private void setStatus(){

    }
}
