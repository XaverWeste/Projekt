package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.User;
import my_project.model.ui.UiElement.Button;
import my_project.model.ui.UiElement.Inputfield;
import my_project.model.ui.UiElement.UiElement;

import javax.swing.*;

public class SignInScreen extends Screen{

    public enum SignInScreenOption{login,signUp};

    private final SignInScreenOption o;

    public SignInScreen(ProgramController pc,SignInScreenOption o) {
        super(pc);
        this.o=o;
        setUpRest();
    }

    @Override
    void setUp() {
        elements.add(new Inputfield(300,Config.WINDOW_HEIGHT/2-30,400,20,"username",pc));
        elements.add(new Inputfield(300,Config.WINDOW_HEIGHT/2,400,20,"password",pc));
        elements.add(new Button(10,10,50,20,"return",pc,()->pc.showScene(0)));
    }

    private void setUpRest(){
        if(o.equals(SignInScreenOption.login)) {
            elements.add(new Button(500, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Log in", pc, this::checkLogIn));
        }else{
            elements.add(new Button(500, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Sign up", pc, this::checkSignUp));
        }
    }

    public void checkLogIn(){
        String[] data=getData();
        int i=pc.checkLogIn(data[0],data[1]);
        if(i>=0){
            activeIf=null;
            pc.setUser(new User(i,data[0]));
            pc.setUpOverviewScreen();
            pc.showScene(3);
        }else{
            JOptionPane.showMessageDialog(null,"Password and/or username invalid");
        }
    }

    public void checkSignUp(){
        String[] data=getData();
        if(pc.signUp(data[0],data[1])){
            activeIf=null;
            pc.setUpOverviewScreen();
            pc.showScene(3);
        }else{
            JOptionPane.showMessageDialog(null,"username already taken");
        }
    }

    private String[] getData(){
        String username="";
        String password="";
        UiElement element= elements.get(0);
        if(element instanceof Inputfield){
            username=((Inputfield) element).getContent();
            ((Inputfield) element).clear();
        }
        element= elements.get(1);
        if(element instanceof Inputfield){
            password=((Inputfield) element).getContent();
            ((Inputfield) element).clear();
        }
        return new String[]{username,password};
    }
}
