package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Project;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;

import javax.swing.*;

public class ProjectListScreen extends Screen{

    public ProjectListScreen(ProgramController pc){
        super(pc);
    }

    @Override
    void setUp() {
        x = y = 200;
        interactables.add(new Inputfield(50,50,200,20,"name",pc));
        interactables.add(new Button(250, 50, 50, 20, "Search",pc, this::searchProject));
        interactables.add(new Button(880,10,100,20,"zurück",pc,()->pc.showScene(3)));
        searchProject();
    }

    public void searchProject() {
        if (interactables.size() > 3) {
            interactables.subList(3, interactables.size()).clear();
        }
        int i = 0;
        for (Project p:pc.getProjects(getSearchKey())) {
            i++;
            double[] c=getC(i);
            interactables.add(new Button(c[0], c[1], 350, 20, p.getName(),pc, ()->applyToProject(p.getProjektID())));
        }
    }

    private String getSearchKey(){
        Interactable i=interactables.get(0);
        if(i instanceof Inputfield){
            String s=((Inputfield) i).getContent();
            if(!s.equals("")){
                ((Inputfield) i).clear();
                this.resetUp();
                return s;
            }
        }
        return "";
    }

    public void applyToProject(int projectID){
        JOptionPane.showMessageDialog(null,"Beitrits anfrage wurde gesendet");
        pc.applyToProject(projectID);
    }
}
