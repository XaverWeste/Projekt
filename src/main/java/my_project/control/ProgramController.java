package my_project.control;

import KAGO_framework.control.DatabaseController;
import KAGO_framework.control.ViewController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.User;
import my_project.model.ui.screen.*;
import my_project.view.InputManager;

import java.util.Arrays;

public class ProgramController {

    private final ViewController v;
    private final String dbPrefix="X2022_Project_";
    private final DatabaseController databaseController;
    private User user;

    public ProgramController(ViewController viewController){
        v = viewController;
        databaseController = new DatabaseController();
        databaseController.connect();

        setUpScreens();
    }

    private void setUpScreens(){
        setUpScreen(new StartScreen(this),0);
        setUpScreen(new SignInScreen(this),1);
        setUpScreen(new SignUpScreen(this),2);
        setUpScreen(new ProjectOverviewScreen(this),3);
    }

    private void setUpScreen(Screen s,int scene){
        v.createScene();
        v.draw(s,scene);
        v.register(new InputManager(s),scene);
    }

    private void testsql(String sql){
        databaseController.executeStatement(sql);
        System.out.println(Arrays.deepToString(databaseController.getCurrentQueryResult().getData()));
        System.out.println(databaseController.getErrorMessage());
    }

    public void startProgram() {
    }

    public Projekt[] getProjekts(){
        databaseController.executeStatement("SELECT * FROM Projekt;");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "Projekt"); //Kontrolle
        try {
            int length = databaseController.getCurrentQueryResult().getRowCount();
            Projekt[] projekts = new Projekt[length];
            String[][] arr = databaseController.getCurrentQueryResult().getData();

            for (int i = 0; arr.length - 1 > i; i++) {
                Projekt p = new Projekt(Integer.parseInt(arr[i][0]), arr[i][1]);
                projekts[i] = p;
            }
            return projekts;
        }catch(NullPointerException ignored){
            return new Projekt[]{};
        }
    }

    public Task[] getTasks(int projektID){
        databaseController.executeStatement("SELECT * FROM Aufgabe WHERE ProjektID = '" + projektID + "';");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "Task"); //Kontrolle
        int length = databaseController.getCurrentQueryResult().getRowCount();
        Task[] tasks = new Task[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            Task t = new Task(Integer.parseInt(arr[i][0]), arr[i][1], Task.TaskStatus.unknown, Integer.parseInt(arr[i][3])); //TODO Taskstatus
            tasks[i] = t;
        }
        return tasks;
    }

    private User[] requestUserArray(){
        int length = databaseController.getCurrentQueryResult().getRowCount();
        User[] users = new User[length];
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
                "WHERE ProjektID = '" + projektID + "';");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 1"); //Kontrolle
        return requestUserArray();
    }

    public User[] getUserArray(int projektID,int aufgabeID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM ((Benutzer" +
                "INNER JOIN bearbeitet ON Benutzer.BID = bearbeitet.BID)" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE AID = '" + aufgabeID + "' AND WHERE ProjektID = '" + projektID + "';");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 2"); //Kontrolle
        return requestUserArray();
    }

    public User gerUser(int userID){
        databaseController.executeStatement("SELECT * FROM BENUTZER WHERE BID = '" + userID + "';");
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
                    "WHERE BID = '" + userID + "';");
            if(databaseController.getErrorMessage() != null) return false;
        }
        if(newPasswort != null){
            databaseController.executeStatement("Update User Set Passwort = '" + newPasswort + "'" +
                    "WHERE BID = '" + userID + "';");
            if(databaseController.getErrorMessage() != null) return false;
        }
        return true;
    }

    public boolean updateProjekt(int projektID, String newName, int numberTasks){
        if(newName != null){
            databaseController.executeStatement("Update Projekt Set Projektname = '" + newName + "'" +
                    "WHERE PID = '" + projektID + "';");
            if(databaseController.getErrorMessage() != null) return false;
        }
        if(numberTasks >= 0){
            databaseController.executeStatement("Update Projekt Set AnzahlAufgaben = '" + numberTasks + "'" +
                    "WHERE PID = '" + projektID + "';");
            if(databaseController.getErrorMessage() != null) return false;
        }
        return true;
    }

    public boolean updateTask(int taskID, boolean done, String describtion){
            databaseController.executeStatement("Update Aufgabe Set Stand = '" + done + "'" +
                    "WHERE AID = '" + taskID + "';");
            if(databaseController.getErrorMessage() != null) return false;
        if(describtion != null){
            databaseController.executeStatement("Update Aufgabe Set Beschreibung = '" + describtion + "'" +
                    "WHERE AID = '" + taskID + "';");
            if(databaseController.getErrorMessage() != null) return false;
        }
        return true;
    }

    /**
     * Löscht das Projekt, wenn es keine Mitglieder mehr gibt.
     */

    public boolean leaveProjekt(int projektID, int userID){
        databaseController.executeStatement("DELETE FROM bearbeitet" +
                "WHERE BID = '" + userID + "' AND WHERE PID = '" + projektID + "';");
        if(databaseController.getErrorMessage() != null) return false;


        databaseController.executeStatement("SELECT COUNT(PID) FROM bearbeitet" +
                "WHERE PDI = '" + projektID + "';");
        if(databaseController.getCurrentQueryResult().getRowCount() == 0){
            databaseController.executeStatement("DELETE FROM bearbeitet" +
                    "WHERE PID = '" + projektID + "';");
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

    public int checkLogIn(String username,String password){
        databaseController.executeStatement("SELECT Password FROM X2022_Project_User WHERE Username = '"+username+"';");
        if(password.equals(databaseController.getCurrentQueryResult().getData()[0][0])){
            databaseController.executeStatement("SELECT UserID FROM X2022_Project_User WHERE Username = '"+username+"';");
            return Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0]);
        }
        return -1;
    }

    public boolean signUp(String username,String password){
        databaseController.executeStatement("SELECT * FROM X2022_Project_User WHERE Username = '"+username+"';");
        if(Arrays.deepToString(databaseController.getCurrentQueryResult().getData()).equals("[]")){
            databaseController.executeStatement("SELECT MAX(UserID) FROM X2022_Project_User");
            int id=Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0]);
            databaseController.executeStatement("INSERT INTO X2022_Project_User VALUES ("+id+", '"+username+"', '"+password+"')");
            user=new User(id,username);
            return true;
        }else return false;
    }

    private Task.TaskStatus getStatus(int i){
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

    private int getStatus(Task.TaskStatus t){
        if(t.equals(Task.TaskStatus.notStartedYet)) return 1;
        if(t.equals(Task.TaskStatus.workingOn)) return 2;
        if(t.equals(Task.TaskStatus.finished)) return 3;
        if(t.equals(Task.TaskStatus.canceled)) return 4;
        else return 5;
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
