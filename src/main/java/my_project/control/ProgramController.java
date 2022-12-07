package my_project.control;

import KAGO_framework.control.DatabaseController;
import KAGO_framework.control.ViewController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.User;
import my_project.model.screen.Screen;

import java.util.HashMap;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    private ViewController viewController;
    private String database;
    private Projekt[] projekts;
    private Task[] tasks;
    private DatabaseController databaseController;
    private User[] user;
    private HashMap<String, Screen> screens=new HashMap<>();

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;

        databaseController = new DatabaseController();
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
    }

    /**
     * Bei den Projekten steht in der ersten Spalte die id (Primärschlüssel-Integer) und im zweiten der Name (String).
     */

    public Projekt[] getProjekt(){
        databaseController.executeStatement("SELECT Projekt FROM " + database + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "Projekt"); //Kontrolle
        int length = databaseController.getCurrentQueryResult().getRowCount();
        projekts = new Projekt[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            Projekt p = new Projekt(Integer.parseInt(arr[i][0].toString()), arr[i][1].toString());
            projekts[i] = p;
        }
        return projekts;
    }

    /**
     * Bei den Tasks steht in der ersten Spalte die id (Primärschlüssel-Integer), der zweiten eine Beschreibung (String),
     * in der dritten ob es done (boolean) ist und in der vieten die ID (Fremdschlüssel-Integer) des Projektes, zu dem es gehört.
     */

    public Task[] getTasks(int projektID){
        databaseController.executeStatement("SELECT * FROM Aufgabe WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "Task"); //Kontrolle
        int length = databaseController.getCurrentQueryResult().getRowCount();
        tasks = new Task[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            Task t = new Task(Integer.parseInt(arr[i][0].toString()), arr[i][1].toString(), Boolean.parseBoolean(arr[i][2].toString()), Integer.parseInt(arr[i][3].toString()));
            tasks[i] = t;
        }
        return tasks;
    }

    /**
     * Bei den Usern steht in der ersten Spalte die id (Primärschlüssel-Integer) und in der zweiten der Benutzername (String)
     * Man kann User die zu einem Projekt gehören oder User, die eine Bestimmte Aufgabe eines Projektes machen.
     */

    private User[] getUser(){
        int length = databaseController.getCurrentQueryResult().getRowCount();
        user = new User[length];
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            User u = new User(Integer.parseInt(arr[i][0].toString()), arr[i][1].toString());
            user[i] = u;
        }
        return user;
    }

    private User[] requestUser(int projektID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM (Benutzer" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 1"); //Kontrolle
        return getUser();
    }

    private User[] requestUser(int projektID,int aufgabeID){
        databaseController.executeStatement("SELECT Benutzer.BID, Benutzer.Name, Benutzer.Vorname, Benutzer.Passwort" +
                "FROM ((Benutzer" +
                "INNER JOIN bearbeitet ON Benutzer.BID = bearbeitet.BID)" +
                "INNER JOIN gehoertZu ON Benutzer.BID = gehoertZu.BID)" +
                "WHERE AID = " + aufgabeID + " AND WHERE ProjektID = " + projektID + ";");
        if(databaseController.getErrorMessage() != null) System.out.println(databaseController.getErrorMessage() + "User 2"); //Kontrolle
        return getUser();
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }
}
