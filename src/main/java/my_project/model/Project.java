package my_project.model;

public class Project {

    private int projektID;
    private String name;

    public Project(int projektID, String name){
        this.projektID = projektID;
        this.name = name;
    }

    public int getProjektID() {
        return projektID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
