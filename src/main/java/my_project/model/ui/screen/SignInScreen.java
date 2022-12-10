package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.User;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;

import javax.swing.*;

public class SignInScreen extends Screen{

    public SignInScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        interactables.add(new Inputfield(Config.WINDOW_WIDTH/2-200,Config.WINDOW_HEIGHT/2-30,400,20,"username"));
        interactables.add(new Inputfield(Config.WINDOW_WIDTH/2-200,Config.WINDOW_HEIGHT/2,400,20,"password"));
        interactables.add(new Button(Config.WINDOW_WIDTH / 2, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Login", this::checkLogIn));
    }

    public void checkLogIn(){
        String username="";
        String password="";
        Interactable interactable=interactables.get(0);
        if(interactable instanceof Inputfield) username=((Inputfield) interactable).getContent();
        interactable=interactables.get(1);
        if(interactable instanceof Inputfield) password=((Inputfield) interactable).getContent();
        int i=pc.checkLogIn(username,password);
        if(i>=0){
            pc.setUser(new User(i,username));
            pc.showScene(3);
        }else{
            JOptionPane.showMessageDialog(null,"Password und/oder Benutzername ungültig");
        }
    }
}
