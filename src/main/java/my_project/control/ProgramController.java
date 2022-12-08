package my_project.control;

import KAGO_framework.control.DatabaseController;
import KAGO_framework.control.ViewController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.User;
import my_project.model.ui.screen.LogInScreen;
import my_project.model.ui.screen.ProjectOverviewScreen;
import my_project.model.ui.screen.Screen;
import my_project.view.InputManager;

public class ProgramController {

    private ViewController v;
    private String database;
    private Projekt[] projekts;
    private Task[] tasks;
    private DatabaseController databaseController;
    private User[] users;
    private User user;

    public ProgramController(ViewController viewController){
        v = viewController;
        databaseController = new DatabaseController();
        setUpScreen(new LogInScreen(this),0);
        setUpScreen(new ProjectOverviewScreen(this),1);
    }

    private void setUpScreen(Screen s,int scene){
        v.createScene();
        v.draw(s,scene);
        v.register(new InputManager(s),scene);
    }

    public void startProgram() {
    }

    /**
     * Hier starten die Getter von Datensätzen. Man bekommt immer einen Array von Modellen, die angefragt wurden
     */

    public String[][] insertData(){
        databaseController.executeStatement("INSERT INTO table_name " + database + ";");
        String[][] arr = databaseController.getCurrentQueryResult().getData();
        int lenght = databaseController.getCurrentQueryResult().getRowCount();
        for(int i = 0; arr.length-1 > i; i++){

        }
        return databaseController.getCurrentQueryResult().getData();
    }


    public Projekt[] getProjekt(){
        databaseController.executeStatement("SELECT * FROM Projekt;");
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
            Task t = new Task(Integer.parseInt(arr[i][0]), arr[i][1], Task.TaskStatus.unknown, Integer.parseInt(arr[i][3])); //TODO Taskstatus
            tasks[i] = t;
        }
        return tasks;
    }

    private User[] requestUserArray(){
        int length = databaseController.getCurrentQueryResult().getRowCount();
        users = new User[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            User u = new User(Integer.parseInt(arr[i][0]), arr[i][1]);
            users[i] = u;
        }
        return users;
    }

    public User[] getUserArray(int projektID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM (Benutzer" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 1"); //Kontrolle
        return requestUserArray();
    }

    public User[] getUserArray(int projektID,int aufgabeID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM ((Benutzer" +
                "INNER JOIN bearbeitet ON Benutzer.BID = bearbeitet.BID)" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE AID = " + aufgabeID + " AND WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 2"); //Kontrolle
        return requestUserArray();
    }

    /**
     * Hier kann man einen bestimmten User getten.
     */

    public User getUser(int userID){
        databaseController.executeStatement("SELECT * FROM BENUTZER WHERE BID = " + userID + ";");
        int id = Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0].toString());
        String name = databaseController.getCurrentQueryResult().getData()[0][1].toString();
        return new User(id, name);
    }

    /**
     * Update Methoden. Wenn man Attributswerte nicht verändern will null bei Strings, -1 bei Integern
     * und die unveränderte Variante des boolean weitergeben.
     */

    public boolean updateUser(int userID, String newName, String newPasswort){
        if(newName != null){
            databaseController.executeStatement("Update User Set Benutzername = '" + newName + "'" +
                    "WHERE BID = " + userID + ";");
            if(databaseController.getErrorMessage() != null) return false;
        }
        if(newPasswort != null){
            databaseController.executeStatement("Update User Set Passwort = '" + newPasswort + "'" +
                    "WHERE BID = " + userID + ";");
            if(databaseController.getErrorMessage() != null) return false;
        }
        return true;
    }

    public boolean updateProjekt(int projektID, String newName, int numberTasks){
        if(newName != null){
            databaseController.executeStatement("Update Projekt Set Projektname = '" + newName + "'" +
                    "WHERE PID = " + projektID + ";");
            if(databaseController.getErrorMessage() != null) return false;
        }
        if(numberTasks >= 0){
            databaseController.executeStatement("Update Projekt Set AnzahlAufgaben = '" + numberTasks + "'" +
                    "WHERE PID = " + projektID + ";");
            if(databaseController.getErrorMessage() != null) return false;
        }
        return true;
    }

    public boolean updateTask(int taskID, boolean done, String describtion){
            databaseController.executeStatement("Update Aufgabe Set Stand = '" + done + "'" +
                    "WHERE AID = " + taskID + ";");
            if(databaseController.getErrorMessage() != null) return false;
        if(describtion != null){
            databaseController.executeStatement("Update Aufgabe Set Beschreibung = '" + describtion + "'" +
                    "WHERE AID = " + taskID + ";");
            if(databaseController.getErrorMessage() != null) return false;
        }
        return true;
    }

    /**
     * Löscht das Projekt, wenn es keine Mitglieder mehr gibt.
     */

    public boolean leaveProjekt(int projektID, int userID){
        databaseController.executeStatement("DELETE FROM bearbeitet" +
                "WHERE BID = " + userID + "AND WHERE PID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) return false;


        databaseController.executeStatement("SELECT COUNT(PID) FROM bearbeitet" +
                "WHERE PDI = " + projektID + ";");
        if(databaseController.getCurrentQueryResult().getRowCount() == 0){
            databaseController.executeStatement("DELETE FROM bearbeitet" +
                    "WHERE PID = " + projektID + ";");
        }
        return true;
    }

    /**
     * überprüft ob die login Daten passen.
     */

    public boolean login(String benutzername, String passwort){
        databaseController.executeStatement("SELECT * FROM Benutzer" +
                "WHERE Benutzername = '" + benutzername +"' AND WHERE Passwort = '" + passwort + "';");
        return databaseController.getErrorMessage() == null;
    }

    /**
     * überprüft ob ein user mit dem namen in der Datenbank existiert und ob das passwort richtig ist. Gibt die user id zurück, wenn alles richtig, sonst -1
     */

    public int checkLogIn(String username,String passwort){
        //TODO
        return -1;
    }

    public void setUser(User u){
        user=u;
    }

    public void showScene(int i){
        v.showScene(i);
    }

    public void updateProgram(double dt){

    }
}
