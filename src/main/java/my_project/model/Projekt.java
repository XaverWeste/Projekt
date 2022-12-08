package my_project.model;

public class Projekt {

    private int projektID;
    private String name;

    public Projekt(int projektID, String name){
        this.projektID = projektID;
        this.name = name;
    }

    public int getProjektID() {
        return projektID;
    }

    public String getName() {
        return name;
    }

    public void setProjektID(int projektID) {
        this.projektID = projektID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
