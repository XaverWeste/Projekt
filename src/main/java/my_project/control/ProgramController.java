package my_project.control;

import KAGO_framework.control.DatabaseController;
import KAGO_framework.control.ViewController;
import my_project.model.Projekt;
import my_project.model.User;
import my_project.model.screen.Screen;

import java.util.HashMap;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    private ViewController viewController;
    private Projekt[] projekt;
    private DatabaseController databaseController;
    private User user;
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

    public Projekt[] getProjekt(){
        databaseController.executeStatement("SELECT Projekt FROM [Insert Database Name];");
        int length = databaseController.getCurrentQueryResult().getRowCount();
        projekt = new Projekt[length];
        String name;
        int id;
        String[][] arr = databaseController.getCurrentQueryResult().getData();

        for(int i = 0;arr.length-1 > i;i++){
            id = Integer.parseInt(arr[i][0].toString());
            name = arr[i][1].toString();
            Projekt p = new Projekt(id, name);
            projekt[i] = p;
        }
        return projekt;
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }
}
