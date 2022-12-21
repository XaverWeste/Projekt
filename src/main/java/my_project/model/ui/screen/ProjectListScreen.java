package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Project;
import my_project.model.ui.UiElement.Button;
import my_project.model.ui.UiElement.Inputfield;
import my_project.model.ui.UiElement.UiElement;

import javax.swing.*;

public class ProjectListScreen extends Screen{

    public ProjectListScreen(ProgramController pc){
        super(pc);
    }

    @Override
    void setUp() {
        x = y = 200;
        elements.add(new Inputfield(50,50,200,20,"name",pc));
        elements.add(new Button(250, 50, 50, 20, "Search",pc, this::searchProject));
        elements.add(new Button(880,10,100,20,"return",pc,()->pc.showScene(3)));
        searchProject();
    }

    public void searchProject() {
        if (elements.size() > 3) {
            elements.subList(3, elements.size()).clear();
        }
        int i = 0;
        for (Project p:pc.getProjects(getSearchKey())) {
            i++;
            double[] c=getC(i);
            elements.add(new Button(c[0], c[1], 350, 20, p.getName(),pc, ()->applyToProject(p.getProjektID())));
        }
    }

    private String getSearchKey(){
        UiElement i= elements.get(0);
        if(i instanceof Inputfield){
            String s=((Inputfield) i).getContent();
            if(!s.equals("")) {
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
