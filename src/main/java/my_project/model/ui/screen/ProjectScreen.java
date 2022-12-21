package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Event;
import my_project.model.Project;
import my_project.model.Task;
import my_project.model.ui.interactable.*;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.TextField;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProjectScreen extends Screen{

    private Taskfield t;
    private Eventfield e;
    private int id;
    private final Pane taskPane,eventPane;
    private Pane applicationPane;

    public ProjectScreen(ProgramController pc) {
        super(pc);
        taskPane=new Pane(pc,this);
        eventPane=new Pane(pc,this);
        setUpTaskPane();
        setUpEventPane();
        getApplication();
    }

    @Override
    void setUp() {
        Project p=pc.getUser().getProjekt();
        interactables.add(new Combobox(10, 100, 200, 20, "sort by", this::sortBy,pc,"deadline","status","name"));
        interactables.add(new Combobox(780, 100, 200, 20, "sort by", this::sortBy,pc,"date","status","name"));
        interactables.add(new TextField(10,20,"Projektname: "+p.getName()+" ,ProjektID: "+p.getProjektID(),pc));
        t=new Taskfield(this,pc,10);
        e=new Eventfield(this,pc,780);
        interactables.add(t);
        interactables.add(e);
        interactables.add(new Button(880,10,100,20,"leave Project",pc,this::leaveProjekt));
        interactables.add(new Button(880,40,100,20,"close Project",pc,this::closeProject));
    }

    private void setUpTaskPane(){
        interactables.add(taskPane);
        taskPane.add(new Button(600, 100, 100, 20, "save",pc,this::savetask));
        taskPane.add(new Inputfield(300,130,400,20,"name",pc));
        taskPane.add(new Combobox(300,160,50,20,"Tag",this::setStatus,pc,getNumbers(1,31,1)));
        taskPane.add(new Combobox(370,160,50,20,"Monat",this::setStatus,pc,getNumbers(1,12,1)));
        taskPane.add(new Combobox(440,160,200,20,"Jahr",this::setStatus,pc,getNumbers(getCurrentTime()[0], 5,1)));
        taskPane.add(new Combobox(300, 190, 400, 20, "status", this::setStatus,pc,"notStartedYet","workingOn","finished","canceled","unknown"));
        taskPane.add(new Combobox(300, 220, 400, 20, "processed from", this::setStatus,pc,pc.getCollaborators()));
        taskPane.add(new Inputfield(300,250,400,"notes",400,200,pc));
        taskPane.add(new Button(450, 100, 100, 20, "delete",pc,this::delete));
        t.setTasks(pc.getTasks("Name"));
        taskPane.setActive(false);
    }

    private void setUpEventPane(){
        interactables.add(eventPane);
        eventPane.add(new Button(600, 100, 100, 20, "save",pc,this::saveEvent));
        eventPane.add(new Inputfield(300,130,400,20,"name",pc));
        eventPane.add(new Combobox(300,160,40,20,"Tag",this::setStatus,pc,getNumbers(1,31,1)));
        eventPane.add(new Combobox(360,160,40,20,"Monat",this::setStatus,pc,getNumbers(1,12,1)));
        eventPane.add(new Combobox(420,160,80,20,"Jahr",this::setStatus,pc,getNumbers(getCurrentTime()[0], 5,1)));
        eventPane.add(new Combobox(520,160,80,20,"Stunde",this::setStatus,pc,getNumbers(0,24,1)));
        eventPane.add(new Combobox(620,160,80,20,"Minute",this::setStatus,pc,getNumbers(0,12,5)));
        eventPane.add(new Combobox(300,190,400,20,"status",this::setStatus,pc,"canceled"));
        eventPane.add(new Inputfield(300,220,400,"place",400,200,pc));
        eventPane.add(new Inputfield(300,250,400,"description",400,200,pc));
        eventPane.add(new Button(450, 100, 100, 20, "delete",pc,this::delete));
        eventPane.add(new Button(300,280,400,20,"",pc,this::participate));
        eventPane.add(new TextField(300,320,"",pc));
        e.setEvents(pc.getEvents("Name"));
        eventPane.setActive(false);
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
                    case "status" -> e.setEvents(pc.getEvents("Status"));
                    case "date" -> e.setEvents(pc.getEvents("Date"));
                    default -> e.setEvents(pc.getEvents("Name"));
                }
            }
        }
    }

    public void setUpEvent(Event e){
        id=e.getId();
        Interactable i=eventPane.get(1);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(e.getName());
        String[] s=e.getDate().split(":");
        if(s.length>4) {
            i = eventPane.get(2);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[2]);
            i = eventPane.get(3);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[1]);
            i = eventPane.get(4);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[0]);
            i = eventPane.get(5);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[3]);
            i = eventPane.get(6);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[4]);
        }else{
            i = eventPane.get(2);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
            i = eventPane.get(3);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
            i = eventPane.get(4);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
            i = eventPane.get(5);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
            i = eventPane.get(6);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
        }
        i=eventPane.get(9);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(e.getDescription());
        i=eventPane.get(8);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(e.getPlace());
        i=eventPane.get(7);
        if(i instanceof Combobox){
            String st=e.getStatus().toString();
            if(st.equals("over")) ((Combobox) i).updateOptions(st);
            else {
                if (st.equals("canceled")) ((Combobox) i).updateOptions("canceled");
                else ((Combobox) i).updateOptions("canceled", st);
            }
        }
        String[] p=e.getParticipants();
        StringBuilder sb=new StringBuilder();
        int j=0;
        Font font = new Font("Serif", Font.PLAIN, 15);
        while(font.getStringBounds(sb.toString(),new FontRenderContext(new AffineTransform(),true,true)).getWidth()<350&&j<p.length){
            sb.append(p[j]).append(", ");
            j++;
        }
        if(j<p.length-1) sb.append(" and ").append(p.length-j-1).append("more");
        if(sb.toString().toCharArray().length==0) sb.append("currently no participants");
        i=eventPane.get(12);
        if(i instanceof TextField) ((TextField) i).setText(sb.toString());
        i=eventPane.get(11);
        if(i instanceof Button){
            boolean b=false;
            for(String str:p) if(str.equals(pc.getUser().getUsername())) b=true;
            if(!b) ((Button) i).setText("participant");
            else ((Button) i).setText("no longer participate");
        }
        e.correctStatus();
        taskPane.setActive(false);
        eventPane.setActive(true);
    }

    private void saveEvent(){
        Event e=new Event(id,"new Event","",Event.EventStatus.asPlanned,"","");
        Interactable i=eventPane.get(1);
        if(i instanceof Inputfield) e.setName(((Inputfield) i).getContent());
        i=eventPane.get(1);
        if(i instanceof Inputfield) e.setDate(((Inputfield) i).getContent());
        StringBuilder sb=new StringBuilder();
        i= eventPane.get(4);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected()).append(":");
        i=eventPane.get(3);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected()).append(":");
        i= eventPane.get(2);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected()).append(":");
        i=eventPane.get(5);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected()).append(":");
        i= eventPane.get(6);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected());
        e.setDate(sb.toString());
        i=eventPane.get(8);
        if(i instanceof Inputfield) e.setPlace(((Inputfield) i).getContent());
        i=eventPane.get(9);
        if(i instanceof Inputfield) e.setDescription(((Inputfield) i).getContent());
        i=eventPane.get(7);
        if(i instanceof Combobox) e.setStatus(Event.getStatus(((Combobox) i).getSelected()));
        if((e.getId()>-1)) pc.updateEvent(e);
        else pc.createEvent(e);
        e.correctStatus();
        sortBy();
    }

    public void setUpTask(Task t){
        id=t.getId();
        Interactable i=taskPane.get(1);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getName());
        String[] s=t.getDeadline().split(":");
        if(s.length>2) {
            i = taskPane.get(2);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[0]);
            i = taskPane.get(3);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[1]);
            i = taskPane.get(4);
            if (i instanceof Combobox) ((Combobox) i).setCurrent(s[2]);
        }else{
            i = taskPane.get(2);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
            i = taskPane.get(3);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
            i = taskPane.get(4);
            if (i instanceof Combobox) ((Combobox) i).setCurrent("");
        }
        i= taskPane.get(5);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(t.getStatus().toString());
        i= taskPane.get(6);
        if(i instanceof Combobox) ((Combobox) i).setCurrent(pc.getUsername(t.getPF()));
        i= taskPane.get(7);
        if(i instanceof Inputfield) ((Inputfield) i).setStringList(t.getNote());
        eventPane.setActive(false);
        taskPane.setActive(true);
    }

    private void savetask(){
        Task t=new Task(id,"","", Task.TaskStatus.unknown,-1,"");
        Interactable i= taskPane.get(1);
        if(i instanceof Inputfield) t.setName(((Inputfield) i).getContent());
        StringBuilder sb=new StringBuilder();
        i= taskPane.get(2);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected()).append(":");
        i= taskPane.get(3);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected()).append(":");
        i= taskPane.get(4);
        if(i instanceof Combobox) sb.append(((Combobox) i).getSelected());
        t.setDeadline(sb.toString());
        i= taskPane.get(5);
        if(i instanceof Combobox) t.setStatus(Task.getStatus(((Combobox) i).getSelected()));
        i= taskPane.get(6);
        if(i instanceof Combobox) t.setPF(pc.getUserId(((Combobox) i).getSelected()));
        i= taskPane.get(7);
        if(i instanceof Inputfield) t.setNote(((Inputfield) i).getContent());
        if((t.getId()>-1)) pc.updateTask(t);
        else pc.createTask(t);
        sortBy();
    }

    private void delete(){
        if(eventPane.isActive()) pc.deleteEvent(id);
        else pc.deleteTask(id);
        sortBy();
    }

    public void getApplication(){
        applicationPane=new Pane(pc,this);
        if(!pc.getApplications(pc.getUser().getId()).isEmpty()){
            applicationPane.add(new TextField(10, 50, "Application: "+pc.getApplications(pc.getUser().getId()).front(),pc));
            applicationPane.add(new Button(280,40,80,20,"Decline",pc,()->processApplication("declined")));
            applicationPane.add(new Button(370,40,80,20,"Accept",pc,()->processApplication("accepted")));
        }else{
            applicationPane.add(new TextField(10,50,"Currently no applications!",pc));
        }
        applicationPane.setActive(true);
        interactables.add(applicationPane);
    }

    public void processApplication(String status){
        if(!pc.getApplications(pc.getUser().getId()).isEmpty()) {
            pc.processApplications(pc.getApplications(pc.getUser().getId()).front(), pc.getUser().getId(), status);
        }
        interactables.remove(applicationPane);
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

    private int[] getCurrentTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd:hh:mm");
        String[] s=sdf.format(Calendar.getInstance().getTime()).split(":");
        int[] i=new int[s.length];
        for(int j=0;j<i.length;j++) i[j]=Integer.parseInt(s[j]);
        return i;
    }

    private String[] getNumbers(int start,int numbers,int intervall){
        String[] s=new String[numbers];
        for(int i=0;i<numbers;i++) s[i]=String.valueOf(start+i*intervall);
        return s;
    }

    private void participate(){
        pc.participant(id);
        String[] p=pc.getparticipants(id);
        StringBuilder sb=new StringBuilder();
        int j=0;
        Font font = new Font("Serif", Font.PLAIN, 15);
        while(font.getStringBounds(sb.toString(),new FontRenderContext(new AffineTransform(),true,true)).getWidth()<350&&j<p.length){
            sb.append(p[j]).append(", ");
            j++;
        }
        if(j<p.length-1) sb.append(" and ").append(p.length-j-1).append("more");
        if(sb.toString().toCharArray().length==0) sb.append("currently no participants");
        Interactable i=eventPane.get(12);
        if(i instanceof TextField) ((TextField) i).setText(sb.toString());
        i=eventPane.get(11);
        if(i instanceof Button){
            boolean b=false;
            for(String str:p) if(str.equals(pc.getUser().getUsername())) b=true;
            if(!b) ((Button) i).setText("participant");
            else ((Button) i).setText("no longer participate");
        }
    }

    private void setStatus(){

    }
}
