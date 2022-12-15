package my_project.model.ui.screen;

import my_project.Config;
import my_project.control.ProgramController;
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
        interactables.add(new Inputfield(300,Config.WINDOW_HEIGHT/2-30,400,20,"username",pc));
        interactables.add(new Inputfield(300,Config.WINDOW_HEIGHT/2,400,20,"password",pc));
        interactables.add(new Button(500, Config.WINDOW_HEIGHT / 2 + 30, 200, 20, "Sign up",pc, this::checkSignUp));
        interactables.add(new Button(10,10,50,20,"zurück",pc,()->pc.showScene(0)));
    }

    public void checkSignUp(){
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
        if(pc.signUp(username,password)){
            pc.setUpOverviewScreen();
            pc.showScene(3);
        }else{
            JOptionPane.showMessageDialog(null,"Benutzername bereits vergeben");
        }
    }
}
