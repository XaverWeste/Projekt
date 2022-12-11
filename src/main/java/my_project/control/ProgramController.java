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
        //testsql("INSERT INTO X2022_Project_Task VALUES(2,5,1,'test','keine',1)");
        setUpScreens();
    }

    private void setUpScreens(){
        setUpScreen(new StartScreen(this),0);
        setUpScreen(new SignInScreen(this),1);
        setUpScreen(new SignUpScreen(this),2);
    }

    public void setUpOverviewScreen(){
        setUpScreen(new ProjectOverviewScreen(this),3);
    }

    public void setUpProject(Projekt p){
        user.setProjekt(p);
        setUpScreen(new ProjektScreen(this),4);
        v.showScene(4);
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

    public void leaveProjekt(){
        databaseController.executeStatement("DELETE FROM X2022_Project_WorkingOn WHERE UserID="+user.getId()+" AND ProjectID="+user.getProjekt().getProjektID());
        databaseController.executeStatement("SELECT * FROM X2022_Project_WorkingOn WHERE ProjectID="+user.getProjekt().getProjektID());
        if(databaseController.getCurrentQueryResult().getRowCount()==0){
            databaseController.executeStatement("DELETE FROM X2022_Project_Project WHERE ProjectIDID="+user.getProjekt().getProjektID());
            databaseController.executeStatement("DELETE FROM X2022_Project_Task WHERE ProjectIDID="+user.getProjekt().getProjektID());
        }
        user.setProjekt(null);
        v.showScene(3);
    }

    public Projekt[] getProjekts(){
        databaseController.executeStatement("SELECT ProjectID FROM X2022_Project_WorkingOn WHERE UserID="+user.getId());
        int[] ids=new int[databaseController.getCurrentQueryResult().getRowCount()];
        String[][] data=databaseController.getCurrentQueryResult().getData();
        for (int i = 0; i<ids.length; i++) ids[i]= Integer.parseInt(data[i][0]);
        Projekt[] projekts = new Projekt[ids.length];
        for(int i = 0; i<ids.length; i++){
            databaseController.executeStatement("SELECT * FROM X2022_Project_Project WHERE ProjectID="+ids[i]);
            projekts[i]=new Projekt(Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0]),databaseController.getCurrentQueryResult().getData()[0][1]);
        }
        return projekts;
    }

    public Task[] getTasks(String orderBy){
        databaseController.executeStatement("SELECT * FROM X2022_Project_Task WHERE ProjectID="+user.getProjekt().getProjektID()+" ORDER BY "+orderBy+" ASC");
        String[][] data=databaseController.getCurrentQueryResult().getData();
        Task[] tasks=new Task[databaseController.getCurrentQueryResult().getRowCount()];
        for(int i = 0; tasks.length - 1 > i; i++){
            tasks[i]=new Task(Integer.parseInt(data[i][0]),data[i][3],data[i][4],getStatus(Integer.parseInt(data[i][1])),Integer.parseInt(data[i][5]));
        }
        return tasks;
    }

    public void updateTask(Task t){
        databaseController.executeStatement("UPDATE X2022_Project_Task SET Status="+getStatus(t.getStatus())+",Deadline='"+t.getDeadline()+"',ProcessedFrom='"+t.getPF()+"',NAME='"+getStatus(t.getStatus())+"' WHERE TaskID="+t.getId());
    }

    public void createTask(Task t){
        databaseController.executeStatement("SELECT MAX(TaskID) FROM X2022_Project_Task");
        int id=Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0])+1;
        databaseController.executeStatement("INSERT INTO X2022_Project_Task VALUES ("+id+", "+getStatus(t.getStatus())+","+user.getProjekt().getProjektID()+",'"+t.getName()+"','"+t.getDeadline()+"',"+t.getPF()+")");
    }

    public void createProject(String name){
        databaseController.executeStatement("SELECT MAX(ProjectID) FROM X2022_Project_Project");
        int id=Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0])+1;
        databaseController.executeStatement("INSERT INTO X2022_Project_Project VALUES ("+id+", '"+name+"', '-')");
        databaseController.executeStatement("INSERT INTO X2022_Project_WorkingOn VALUES ("+user.getId()+", '"+id+"')");
        user.setProjekt(new Projekt(id,name));
    }

    public void joinProject(int id){
        databaseController.executeStatement("SELECT * FROM X2022_Project_WorkingOn WHERE UserID="+user.getId()+" AND ProjectID="+id);
        if(databaseController.getCurrentQueryResult().getRowCount()==0) databaseController.executeStatement("INSERT INTO X2022_Project_WorkingOn VALUES ("+user.getId()+", '"+id+"')");
    }

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
            int id=Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0])+1;
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

    public User getUser(){
        return user;
    }

    public void showScene(int i){
        v.showScene(i);
    }

    public void updateProgram(double dt){

    }
}
