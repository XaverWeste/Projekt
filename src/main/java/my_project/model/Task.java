package my_project.model;

public class Task {

    public enum TaskStatus{notStartedYet,workingOn,finished,canceled,unknown}

    private int id;
    private int projektID;
    private String describtion;
    private TaskStatus status;

    public Task(int id, String describtion, TaskStatus status, int projektID){
        this.id = id;
        this. describtion = describtion;
        this.status=status;
        this.projektID = projektID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getProjektID() {
        return projektID;
    }

    public void setProjektID(int projektID) {
        this.projektID = projektID;
    }
}
