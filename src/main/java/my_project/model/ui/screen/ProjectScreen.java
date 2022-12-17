package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Project;
import my_project.model.Task;
import my_project.model.ui.interactable.*;

public class ProjectScreen extends Screen{

    private Taskfield t;
    private int id;
    private final Pane taskPane;

    public ProjectScreen(ProgramController pc) {
        super(pc);
        taskPane=new Pane(pc, this);
        setUpTaskPane();
    }

    @Override
    void setUp() {
        Project p=pc.getUser().getProjekt();
        interactables.add(new TextField(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID(),pc));
        t=new Taskfield(this,pc);
        interactables.add(t);
        interactables.add(new Button(880,10,100,20,"leave Project",pc,this::leaveProjekt));
        interactables.add(new Button(880,40,100,20,"close Project",pc,this::closeProject));
    }

    private void setUpTaskPane(){
        interactables.add(taskPane);
        taskPane.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,pc,"deadline","status","name"));
        taskPane.add(new Button(600, 100, 100, 20, "save",pc,this::save));
        taskPane.add(new Inputfield(300,130,400,20,"name",pc));
        taskPane.add(new Inputfield(300,160,400,20,"deadline",pc));
        taskPane.add(new Combobox(300, 190, 400, 20, "status", this::setStatus,pc,"notStartedYet","workingOn","finished","canceled","unknown"));
        taskPane.add(new Combobox(300, 220, 400, 20, "processed from", this::setStatus,pc,pc.getCollaborators()));
        taskPane.add(new Inputfield(300,250,400,200,"notes",400,200,pc));
        t.setTasks(pc.getTasks("Name"));
    }

    private void sortBy(){
        Interactable i=taskPane.get(0);
        if(i instanceof Combobox){
            switch(((Combobox) i).getSelected()){
                case "status" -> t.setTasks(pc.getTasks("Status"));
                case "deadline" -> t.setTasks(pc.getTasks("Deadline"));
                default -> t.setTasks(pc.getTasks("Name"));
            }
        }
    }

    public void setUpTask(Task t){
        id=t.getId();
        Interactable i=taskPane.get(2);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getName());
        i= taskPane.get(3);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getDeadline());
        i= taskPane.get(4);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(t.getStatus().toString());
        i= taskPane.get(5);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(pc.getUsername(t.getPF()));
        taskPane.setActive(true);
    }

    private void save(){
        Task t=new Task(id,"","", Task.TaskStatus.unknown,-1,"");
        Interactable i= taskPane.get(2);
        if(i instanceof Inputfield) t.setName(((Inputfield) i).getContent());
        i= taskPane.get(3);
        if(i instanceof Inputfield) t.setDeadline(((Inputfield) i).getContent());
        i= taskPane.get(4);
        if(i instanceof Combobox) t.setStatus(pc.getStatus(((Combobox) i).getSelected()));
        i= taskPane.get(5);
        if(i instanceof Combobox) t.setPF(pc.getUserId(((Combobox) i).getSelected()));
        if((t.getId()>-1)) pc.updateTask(t);
        else pc.createTask(t);
        sortBy();
    }

    private void leaveProjekt(){
        pc.leaveProjekt();
        pc.showScene(3);
    }

    private void closeProject(){
        pc.getUser().setProjekt(null);
        pc.showScene(3);
    }

    public void setActifeField(Inputfield i){
        activeIf=i;
    }

    private void setStatus(){

    }
}
