package my_project.model;

public class Task {

    private int id;
    private int projektID;
    private String describtion;
    private boolean done;

    public Task(int id, String describtion, boolean done, int projektID){
        this.id = id;
        this. describtion = describtion;
        this.done = done;
        this.projektID = projektID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public int getProjektID() {
        return projektID;
    }

    public void setProjektID(int projektID) {
        this.projektID = projektID;
    }
}
