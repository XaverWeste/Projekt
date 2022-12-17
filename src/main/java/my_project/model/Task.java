package my_project.model;

public class Task {

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
