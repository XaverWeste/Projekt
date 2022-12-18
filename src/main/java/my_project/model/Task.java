package my_project.model;

public class Task extends Viewable{

    public enum TaskStatus{notStartedYet,workingOn,finished,canceled,unknown}

    private int id,processedFrom;
    private String deadline,name;
    private TaskStatus status;
    private String notes;

    public Task(int id,String name, String deadline, TaskStatus status,int processedFrom,String note){
        this.id = id;
        this.name=name;
        this.deadline = deadline;
        this.status=status;
        this.processedFrom=processedFrom;
        notes=note;
    }

    public static Task.TaskStatus getStatus(int i){
        switch(i){
            case 1 -> {
                return Task.TaskStatus.notStartedYet;
            }
            case 2 -> {
                return Task.TaskStatus.workingOn;
            }
            case 3 -> {
                return Task.TaskStatus.finished;
            }
            case 4 -> {
                return Task.TaskStatus.canceled;
            }
            default -> {
                return Task.TaskStatus.unknown;
            }
        }
    }

    public static Task.TaskStatus getStatus(String s){
        switch(s){
            case "notStartedYet" -> {
                return Task.TaskStatus.notStartedYet;
            }
            case "workingOn" -> {
                return Task.TaskStatus.workingOn;
            }
            case "finished" -> {
                return Task.TaskStatus.finished;
            }
            case "canceled" -> {
                return Task.TaskStatus.canceled;
            }
            default -> {
                return Task.TaskStatus.unknown;
            }
        }
    }

    public static int getStatus(Task.TaskStatus t){
        if(t.equals(Task.TaskStatus.notStartedYet)) return 1;
        if(t.equals(Task.TaskStatus.workingOn)) return 2;
        if(t.equals(Task.TaskStatus.finished)) return 3;
        if(t.equals(Task.TaskStatus.canceled)) return 4;
        else return 5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getPF() {
        return processedFrom;
    }

    public void setPF(int id) {
        processedFrom = id;
    }

    public String getNote() {
        return notes;
    }

    public void setNote(String note) {
        notes=note;
    }
}
