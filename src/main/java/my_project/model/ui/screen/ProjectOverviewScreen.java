package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Project;
import my_project.model.ui.UiElement.Button;
import my_project.model.ui.UiElement.Inputfield;
import my_project.model.ui.UiElement.UiElement;
import my_project.model.ui.UiElement.TextField;

public class ProjectOverviewScreen extends Screen{

    public ProjectOverviewScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        elements.add(new Inputfield(300,100,200,20,"Project name",pc));
        elements.add(new Button(510, 100, 200, 20, "create Project",pc, this::createProject));
        elements.add(new Button(880,10,100,20,"Log out",pc,this::logout));
        elements.add(new Button(50,100,200,20,"Search Project",pc, this::searchProjekt));
        elements.add(new TextField(270,115,"or",pc));
        int i=0;
        for(Project p:pc.getYourProjects()){
            i++;
            double[] c=getC(i);
            elements.add(new Button(c[0], c[1], 350, 20, p.getName(),pc,()->selectProjekt(p)));
        }
    }

    private void joinProject(){
        UiElement i= elements.get(0);
        if(i instanceof Inputfield){
            String s=((Inputfield) i).getContent();
            if(!s.equals("")){
                pc.joinProject(Integer.parseInt(s));
                ((Inputfield) i).clear();
                this.resetUp();
            }
        }
    }

    private void createProject(){
        UiElement i= elements.get(2);
        if(i instanceof Inputfield){
            String s=((Inputfield) i).getContent();
            if(!s.equals("")){
                pc.createProject(s);
                ((Inputfield) i).clear();
                this.resetUp();
            }
        }
    }

    private void selectProjekt(Project p){
        pc.setUpProject(p);
    }

    private void searchProjekt(){
        pc.setUpListScreen();
    }

    private void logout(){
        pc.setUser(null);
        pc.showScene(0);
    }
}
