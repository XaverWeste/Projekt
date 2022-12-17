package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.User;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;
import my_project.model.ui.interactable.TextField;

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
        interactables.add(new Inputfield(300,Config.WINDOW_HEIGHT/2-30,400,20,"username",pc));
        interactables.add(new Inputfield(300,Config.WINDOW_HEIGHT/2,400,20,"password",pc));
        interactables.add(new Button(10,10,50,20,"zurück",pc,()->pc.showScene(0)));
    }

    private void setUpRest(){
        if(o.equals(SignInScreenOption.login)) {
            interactables.add(new Button(500, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Log in", pc, this::checkLogIn));
        }else{
            interactables.add(new Button(500, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Sig up", pc, this::checkSignUp));
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
            JOptionPane.showMessageDialog(null,"Password und/oder Benutzername ungültig");
        }
    }

    public void checkSignUp(){
        String[] data=getData();
        if(pc.signUp(data[0],data[1])){
            activeIf=null;
            pc.setUpOverviewScreen();
            pc.showScene(3);
        }else{
            JOptionPane.showMessageDialog(null,"Benutzername bereits vergeben");
        }
    }

    private String[] getData(){
        String username="";
        String password="";
        Interactable interactable=interactables.get(0);
        if(interactable instanceof Inputfield){
            username=((Inputfield) interactable).getContent();
            ((Inputfield) interactable).clear();
        }
        interactable=interactables.get(1);
        if(interactable instanceof Inputfield){
            password=((Inputfield) interactable).getContent();
            ((Inputfield) interactable).clear();
        }
        return new String[]{username,password};
    }
}
