package my_project.model;

public class User {

    private String username;
    private int id;
    private Project workingOn=null;

    public User(int id, String username){
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProjekt() {
        return workingOn;
    }

    public void setProjekt(Project p) {
        workingOn=p;
    }
}
