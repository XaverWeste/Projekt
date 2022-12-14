package my_project.model.ui.screen;

import my_project.control.ProgramController;
import my_project.model.Projekt;
import my_project.model.ui.interactable.Button;
import my_project.model.ui.interactable.Inputfield;
import my_project.model.ui.interactable.Interactable;

public class ProjectOverviewScreen extends Screen{

    public ProjectOverviewScreen(ProgramController pc) {
        super(pc);
    }

    @Override
    void setUp() {
        interactables.add(new Inputfield(50,50,200,20,"ProjektID"));
        interactables.add(new Button(300, 50, 200, 20, "trete dem Projekt bei", this::joinProjekt));
        interactables.add(new Inputfield(50,100,200,20,"Projektname"));
        interactables.add(new Button(300, 100, 200, 20, "erstelle ein neues Projekt", this::createProjekt));
        interactables.add(new Button(880,10,100,20,"Log out",this::logout));
        int i=0;
        for(Projekt p:pc.getProjekts()){
            i++;
            double[] c=getC(i);
            interactables.add(new Button(c[0], c[1], 350, 20, p.getName(),()->selectProjekt(p)));
        }
    }

    private void joinProjekt(){
        Interactable i=interactables.get(0);
        if(i instanceof Inputfield){
            String s=((Inputfield) i).getContent();
            if(!s.equals("")){
                pc.joinProject(Integer.parseInt(s));
                ((Inputfield) i).clear();
                this.resetUp();
            }
        }
    }

    private void createProjekt(){
        Interactable i=interactables.get(2);
        if(i instanceof Inputfield){
            String s=((Inputfield) i).getContent();
            if(!s.equals("")){
                pc.createProject(s);
                ((Inputfield) i).clear();
                this.resetUp();
            }
        }
    }

    private void selectProjekt(Projekt p){
        pc.setUpProject(p);
    }

    private double[] getC(int i){
        double[] arr=new double[2];
        if(i%2==0) arr[0]=550;
        else{
            arr[0]=100;
            i++;
        }
        arr[1]=30*(i/2)+150;
        return arr;
    }

    private void logout(){
        pc.setUser(null);
        pc.showScene(0);
    }
}
