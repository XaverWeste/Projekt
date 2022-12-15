package my_project.control;

import KAGO_framework.control.DatabaseController;
import KAGO_framework.control.ViewController;
import my_project.model.Projekt;
import my_project.model.Task;
import my_project.model.User;
import my_project.model.ui.Theme;
import my_project.model.ui.screen.*;
import my_project.view.InputManager;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;

public class ProgramController {

    private final ViewController v;
    private final DatabaseController databaseController;
    private User user;
    private HashMap<String,Theme> themes=new HashMap<>();
    private Theme activeT=null;
    private Screen po,p,pl;

    public ProgramController(ViewController viewController){
        v = viewController;
        databaseController = new DatabaseController();
        databaseController.connect();
        setUpThemes();
        //testsql("SELECT ProjectID FROM X2022_Project_WorkingOn WHERE UserID=1 AND Joined = 'true'");
        setUpScreens();
    }

    private void setUpThemes(){
        themes.put("Dark",new Theme(Color.DARK_GRAY,Color.BLACK,Color.GRAY,Color.BLACK,Color.DARK_GRAY,Color.RED));
        themes.put("Light",new Theme(Color.WHITE,Color.BLACK,Color.LIGHT_GRAY,Color.BLACK,Color.GRAY,Color.ORANGE));
        themes.put("Rainbow",new Theme(Color.WHITE,Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA));
        setTheme("Rainbow");
    }

    public void setTheme(String name){
        if(themes.containsKey(name)) activeT=themes.get(name);
    }

    private void setUpScreens(){
        setUpScreen(new StartScreen(this),0);
        setUpScreen(new SignInScreen(this),1);
        setUpScreen(new SignUpScreen(this),2);
    }

    public void setUpOverviewScreen(){
        po=new ProjectOverviewScreen(this);
        setUpScreen(po,3);
    }

    public void setUpProject(Projekt p){
        user.setProjekt(p);
        this.p=new ProjektScreen(this);
        setUpScreen(this.p,4);
        v.showScene(4);
    }

    public void setUpListScreen(){
        this.pl=new ProjectListScreen(this);
        setUpScreen(this.pl,5);
        v.showScene(5);
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

    public String[] getCollaborators(){
        databaseController.executeStatement("SELECT UserID FROM X2022_Project_WorkingOn WHERE ProjectID="+user.getProjekt().getProjektID());
        String[] s=new String[databaseController.getCurrentQueryResult().getRowCount()];
        String[][] data=databaseController.getCurrentQueryResult().getData();
        for(int i=0;i<s.length;i++) {
            databaseController.executeStatement("SELECT Username FROM X2022_Project_User WHERE UserID="+data[i][0]);
            s[i]=databaseController.getCurrentQueryResult().getData()[0][0];
        }
        return s;
    }

    public int getUserId(String name){
        databaseController.executeStatement("SELECT UserID FROM X2022_Project_User WHERE Username='"+name+"'");
        return Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0]);
    }

    public String getUsername(int id){
        databaseController.executeStatement("SELECT Username FROM X2022_Project_User WHERE UserID="+id);
        return databaseController.getCurrentQueryResult().getData()[0][0];
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

    public Projekt[] getProjekts(String key){
        databaseController.executeStatement("SELECT Name FROM X2022_Project_Project WHERE Invisible = 'false';");
        String[][] data=databaseController.getCurrentQueryResult().getData();
        Projekt[] projekts = new Projekt[data.length];
        for(int i = 0; i<data.length- 1; i++){
            projekts[i]=new Projekt(Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0]),databaseController.getCurrentQueryResult().getData()[0][1]);
        }
        return projekts;
    }

    public Projekt[] getYourProjekts(){
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
        for(int i = 0; tasks.length > i; i++){
            tasks[i]=new Task(Integer.parseInt(data[i][0]),data[i][3],data[i][4],getStatus(Integer.parseInt(data[i][1])),Integer.parseInt(data[i][5]));
        }
        return tasks;
    }

    public void updateTask(Task t){
        databaseController.executeStatement("UPDATE X2022_Project_Task SET Status="+getStatus(t.getStatus())+",Deadline='"+t.getDeadline()+"',ProcessedFrom='"+t.getPF()+"',NAME='"+t.getName()+"' WHERE TaskID="+t.getId());
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
        databaseController.executeStatement("SELECT * FROM X2022_Project_User WHERE Username = '"+username+"';");
        String[][] data=databaseController.getCurrentQueryResult().getData();
        if(Arrays.deepToString(databaseController.getCurrentQueryResult().getData()).equals("[]")){
            String salt = data[0][3];
            if (hash(password.toCharArray(), hexToBytes(salt)).equals(data[0][2])) {
                databaseController.executeStatement("SELECT UserID FROM X2022_Project_User WHERE Username = '" + username + "';");
                return Integer.parseInt(data[0][0]);
            }
        }
        return -1;
    }

    public boolean signUp(String username,String password){
        databaseController.executeStatement("SELECT * FROM X2022_Project_User WHERE Username = '"+username+"';");
        if(Arrays.deepToString(databaseController.getCurrentQueryResult().getData()).equals("[]")){
            databaseController.executeStatement("SELECT MAX(UserID) FROM X2022_Project_User");
            int id=Integer.parseInt(databaseController.getCurrentQueryResult().getData()[0][0])+1;
            byte[] salt=generateSalt();
            databaseController.executeStatement("INSERT INTO X2022_Project_User VALUES ("+id+", '"+username+"', '"+hash(password.toCharArray(),salt)+"','"+bytesToHex(salt)+"')");
            user=new User(id,username);
            return true;
        }else return false;
    }

    private byte[] generateSalt() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private String hash(char[] input, byte[] salt) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");
            messageDigest.update(salt);
            final byte[] hashedBytes = messageDigest.digest(new String(input).getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException ignored) {return null;}
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash){
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len/2];
        for (int i = 0; i < len; i += 2) bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i+1), 16));
        return bytes;
    }

    public Task.TaskStatus getStatus(int i){
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

    public Task.TaskStatus getStatus(String s){
        switch(s){
            case "notStartedYet" -> {
                return Task.TaskStatus.notStartedYet;
            }
            case "workingOn" -> {
                return Task.TaskStatus.workingOn;
            }
            case "finished" -> {
                return Task.TaskStatus.finished;
            }
            case "canceled" -> {
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
        switch(i){
            case 3 -> po.resetUp();
            case 4 -> p.resetUp();
        }
        v.showScene(i);
    }

    public Theme getTheme(){
        return activeT;
    }

    public void updateProgram(double dt){

    }
}
