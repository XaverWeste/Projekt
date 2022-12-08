package my_project.model.interactable;

public class Inputfield extends Interactable{

    private String s;

    public void clear(){
        s="";
    }

    public void add(char c){
        s+=c;
    }

    public void clearLast(){
        char[] c=s.toCharArray();
        clear();
        for(int i=0;i<c.length-1;i++) add(c[i]);
    }

    public boolean clickOn(double x,double y){
        if(x>this.x&&y>this.y&&x<this.x+width&&y<this.y+height) return true;
        return false;
    }
}
