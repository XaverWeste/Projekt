package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;
import my_project.model.ui.interactable.TextField;

public class ProjectListScreen extends Screen{

    private Projekt[] projektList;

    public ProjectListScreen(ProgramController pc){
        super(pc);
        searchProjekt();
    }

    @Override
    void setUp() {
        interactables.add(new Inputfield(50,50,200,20,"name",pc));
        interactables.add(new Button(250, 50, 50, 20, "Search",pc, this::searchProjekt));
    }




    public void searchProjekt() {
        projektList = pc.getProjekts(getSearchKey());
        for (int i = 0; i < projektList.length - 1; i++) {
            interactables.add(new Button(x, y, 50, 20, projektList[i].getName(),pc, this::applyToProject));
            y += 30;
            if (y > 600) {
                y = 200;
                x += 300;
            }
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

    public void applyToProject(){

    }
}
