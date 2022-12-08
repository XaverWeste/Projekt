package my_project.control;

import KAGO_framework.control.DatabaseController;
import KAGO_framework.control.ViewController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.User;
import my_project.model.ui.screen.LogInScreen;
import my_project.model.ui.screen.Screen;
import my_project.view.InputManager;

public class ProgramController {

    private ViewController v;
    private String database;
    private Projekt[] projekts;
    private Task[] tasks;
    private DatabaseController databaseController;
    private User[] user;

    public ProgramController(ViewController viewController){
        v = viewController;
        databaseController = new DatabaseController();
        setUpScreen(new LogInScreen(this),0);
    }

    private void setUpScreen(Screen s,int scene){
        v.createScene();
        v.draw(s,scene);
        v.register(new InputManager(s),scene);
    }

    public void startProgram() {
    }

    public Projekt[] getProjekt(){
        databaseController.executeStatement("SELECT Projekt FROM " + database + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "Projekt"); //Kontrolle
        int length = databaseController.getCurrentQueryResult().getRowCount();
        projekts = new Projekt[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            Projekt p = new Projekt(Integer.parseInt(arr[i][0]), arr[i][1]);
            projekts[i] = p;
        }
        return projekts;
    }

    public Task[] getTasks(int projektID){
        databaseController.executeStatement("SELECT * FROM Aufgabe WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "Task"); //Kontrolle
        int length = databaseController.getCurrentQueryResult().getRowCount();
        tasks = new Task[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            Task t = new Task(Integer.parseInt(arr[i][0]), arr[i][1], Boolean.parseBoolean(arr[i][2]), Integer.parseInt(arr[i][3]));
            tasks[i] = t;
        }
        return tasks;
    }

    private User[] getUser(){
        int length = databaseController.getCurrentQueryResult().getRowCount();
        user = new User[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            User u = new User(Integer.parseInt(arr[i][0]), arr[i][1]);
            user[i] = u;
        }
        return user;
    }

    public User[] requestUser(int projektID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM (Benutzer" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 1"); //Kontrolle
        return getUser();
    }

    public User[] requestUser(int projektID,int aufgabeID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM ((Benutzer" +
                "INNER JOIN bearbeitet ON Benutzer.BID = bearbeitet.BID)" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE AID = " + aufgabeID + " AND WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 2"); //Kontrolle
        return getUser();
    }

    /**
     * überprüft ob ein user mit dem namen in der Datenbank existiert und ob das passwort richtig ist. Gibt die user id zurück, wenn alles richtig, sonst -1
     */
    public int checkLogIn(String username,String passwort){
        //TODO
        return -1;
    }

    public void updateProgram(double dt){

    }
}
