package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
import my_project.model.User;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;

import javax.swing.*;

public class SignUpScreen extends Screen{

    public SignUpScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        interactables.add(new Inputfield(Config.WINDOW_WIDTH/2-200,Config.WINDOW_HEIGHT/2-30,400,20,"username"));
        interactables.add(new Inputfield(Config.WINDOW_WIDTH/2-200,Config.WINDOW_HEIGHT/2,400,20,"password"));
        interactables.add(new Button(Config.WINDOW_WIDTH / 2, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Sign up", this::checkSignUp));
    }

    public void checkSignUp(){
        String username="";
        String password="";
        Interactable interactable=interactables.get(0);
        if(interactable instanceof Inputfield) username=((Inputfield) interactable).getContent();
        interactable=interactables.get(1);
        if(interactable instanceof Inputfield) password=((Inputfield) interactable).getContent();
        if(pc.signUp(username,password)){
            pc.setUpRestScreens();
            pc.showScene(3);
        }else{
            JOptionPane.showMessageDialog(null,"Benutzername bereits vergeben");
        }
    }
}
