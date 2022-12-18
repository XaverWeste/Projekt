package my_project.model.ui.screen;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import my_project.control.ProgramController;
import my_project.model.Event;
import my_project.model.Project;
import my_project.model.Task;
import my_project.model.ui.interactable.*;

public class ProjectScreen extends Screen{

    private Taskfield t;
    private Eventfield e;
    private int id,projectID;
    private final Pane taskPane,eventPane;

    public ProjectScreen(ProgramController pc) {
        super(pc);
        taskPane=new Pane(pc, this);
        eventPane=new Pane(pc,this);
        projectID = pc.getUser().getProjekt().getProjektID();
        setUpTaskPane();
        setUpEventPane();
        getApplication();
    }

    @Override
    void setUp() {
        Project p=pc.getUser().getProjekt();
        interactables.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,pc,"deadline","status","name"));
        interactables.add(new Combobox(790, 100, 200, 20, "sort by", this::sortBy,pc,"date","status","name"));
        interactables.add(new TextField(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID(),pc));
        t=new Taskfield(this,pc,10);
        e=new Eventfield(this,pc,790);
        interactables.add(t);
        interactables.add(e);
        interactables.add(new Button(880,10,100,20,"leave Project",pc,this::leaveProjekt));
        interactables.add(new Button(880,40,100,20,"close Project",pc,this::closeProject));
        interactables.add(new Button(800,275,80,20,"Decline",pc,()->processApplication("declined")));
        interactables.add(new Button(880,275,80,20,"Accept",pc,()->processApplication("accepted")));
    }

    private void setUpTaskPane(){
        interactables.add(taskPane);
        taskPane.add(new Button(600, 100, 100, 20, "save",pc,this::savetask));
        taskPane.add(new Inputfield(300,130,400,20,"name",pc));
        taskPane.add(new Inputfield(300,160,400,20,"deadline",pc));
        taskPane.add(new Combobox(300, 190, 400, 20, "status", this::setStatus,pc,"notStartedYet","workingOn","finished","canceled","unknown"));
        taskPane.add(new Combobox(300, 220, 400, 20, "processed from", this::setStatus,pc,pc.getCollaborators()));
        taskPane.add(new Inputfield(300,250,400,"notes",400,200,pc));
        t.setTasks(pc.getTasks("Name"));
    }

    private void setUpEventPane(){
        interactables.add(eventPane);
        taskPane.add(new Button(600, 100, 100, 20, "save",pc,this::saveEvent));
        taskPane.add(new Inputfield(300,130,400,20,"name",pc));
        taskPane.add(new Inputfield(300,160,400,20,"date",pc));
        taskPane.add(new Inputfield(300,190,400,"notes",400,200,pc));
        e.setEvents(pc.getEvents("Name"));
    }

    private void sortBy(){
        if(taskPane.isActive()) {
            Interactable i = interactables.get(0);
            if (i instanceof Combobox) {
                switch (((Combobox) i).getSelected()) {
                    case "status" -> t.setTasks(pc.getTasks("Status"));
                    case "deadline" -> t.setTasks(pc.getTasks("Deadline"));
                    default -> t.setTasks(pc.getTasks("Name"));
                }
            }
        }else if(eventPane.isActive()){
            Interactable i = interactables.get(1);
            if (i instanceof Combobox) {
                switch (((Combobox) i).getSelected()) {
                    case "status" -> t.setTasks(pc.getTasks("Status"));
                    case "date" -> t.setTasks(pc.getTasks("Date"));
                    default -> t.setTasks(pc.getTasks("Name"));
                }
            }
        }
    }

    public void setUpEvent(Event e){
        id=e.getId();
        taskPane.setActive(false);
        eventPane.setActive(true);
    }

    private void saveEvent(){
        new Event(-1,"new Event","",Event.EventStatus.unknown,"",new int[]{pc.getUser().getId()});

        sortBy();
    }

    public void setUpTask(Task t){
        id=t.getId();
        Interactable i=taskPane.get(1);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getName());
        i= taskPane.get(2);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getDeadline());
        i= taskPane.get(3);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(t.getStatus().toString());
        i= taskPane.get(4);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(pc.getUsername(t.getPF()));
        i= taskPane.get(5);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getNote());
        eventPane.setActive(false);
        taskPane.setActive(true);
    }

    private void savetask(){
        Task t=new Task(id,"","", Task.TaskStatus.unknown,-1,"");
        Interactable i= taskPane.get(1);
        if(i instanceof Inputfield) t.setName(((Inputfield) i).getContent());
        i= taskPane.get(2);
        if(i instanceof Inputfield) t.setDeadline(((Inputfield) i).getContent());
        i= taskPane.get(3);
        if(i instanceof Combobox) t.setStatus(Task.getStatus(((Combobox) i).getSelected()));
        i= taskPane.get(4);
        if(i instanceof Combobox) t.setPF(pc.getUserId(((Combobox) i).getSelected()));
        i= taskPane.get(5);
        if(i instanceof Inputfield) t.setNote(((Inputfield) i).getContent());
        if((t.getId()>-1)) pc.updateTask(t);
        else pc.createTask(t);
        sortBy();
    }

    public void getApplication(){
        System.out.println("Application: "+pc.getApplications(projectID).front());
        if(interactables.size() > 9) interactables.remove(9);
            if(!pc.getApplications(projectID).isEmpty()){
                interactables.add(new TextField(810, 250, "Application: "+pc.getApplications(projectID).front(),pc));
            }else{
                interactables.add(new TextField(810,250,"Currently no applications!",pc));
            }
    }

    public void processApplication(String status){
        if(!pc.getApplications(projectID).isEmpty()) {
            pc.processApplications(pc.getApplications(projectID).front(), projectID, status);
        }
        getApplication();
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
