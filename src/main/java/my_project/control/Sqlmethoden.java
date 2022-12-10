package my_project.control;

public class Sqlmethoden {
    /*
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

    public boolean login(String benutzername, String passwort){
        databaseController.executeStatement("SELECT * FROM Benutzer" +
                "WHERE Benutzername = '" + benutzername +"' AND WHERE Passwort = '" + passwort + "';");
        return databaseController.getErrorMessage() == null;
    }

     */


}
