package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;
import my_project.model.ui.interactable.TextField;

public class ProjectListScreen extends Screen{

    private Projekt[] projektList;
    private Projekt p;

    public ProjectListScreen(ProgramController pc){
        super(pc);
        searchProjekt();
    }

    @Override
    void setUp() {
        x = y = 200;
        interactables.add(new Inputfield(50,50,200,20,"name",pc));
        interactables.add(new Button(250, 50, 50, 20, "Search",pc, this::searchProjekt));
    }




    public void searchProjekt() {
        if (interactables.size() > 2) {
            interactables.subList(2, interactables.size()).clear();
        }
        int i = 0;
        for (Projekt p:pc.getProjekts(getSearchKey())) {
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
        pc.applyToProjekt(projectID);
    }
}
