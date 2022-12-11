package my_project.model;

public class Task {

    public enum TaskStatus{notStartedYet,workingOn,finished,canceled,unknown}

    private int id,processedFrom;
    private String deadline,name;
    private TaskStatus status;

    public Task(int id,String name, String deadline, TaskStatus status,int processedFrom){
        this.id = id;
        this.deadline = deadline;
        this.status=status;
        this.processedFrom=processedFrom;
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
}
