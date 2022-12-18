package my_project.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Event extends Viewable{

    public enum EventStatus{asPlanned, moved,over,canceled,unknown}

    private int id;
    private String date,name,description,place;
    private Event.EventStatus status;
    private int[] participants;

    public Event(int id, String name, String date, Event.EventStatus status, String description, String place){
        this.id=id;
        this.name=name;
        this.date=date;
        this.status=status;
        this.description=description;
    }

    public static Event.EventStatus getStatus(int i){
        switch(i){
            case 1 -> {
                return EventStatus.asPlanned;
            }
            case 2 -> {
                return EventStatus.moved;
            }
            case 3 -> {
                return EventStatus.over;
            }
            case 4 -> {
                return EventStatus.canceled;
            }
            default -> {
                return Event.EventStatus.unknown;
            }
        }
    }

    public static Event.EventStatus getStatus(String s){
        switch(s){
            case "asPlanned" -> {
                return EventStatus.asPlanned;
            }
            case "moved" -> {
                return EventStatus.moved;
            }
            case "over" -> {
                return EventStatus.over;
            }
            case "canceled" -> {
                return Event.EventStatus.canceled;
            }
            default -> {
                return Event.EventStatus.unknown;
            }
        }
    }

    public static int getStatus(Event.EventStatus t){
        if(t.equals(EventStatus.unknown)) return 1;
        if(t.equals(EventStatus.moved)) return 2;
        if(t.equals(EventStatus.over)) return 3;
        if(t.equals(Event.EventStatus.canceled)) return 4;
        else return 5;
    }

    private void corecctStatus(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd:hh:mm");
        try{
            boolean b=false;
            String[] sd=date.split(":");
            String[] sc=sdf.format(c.getTime()).split(":");
            for(int i=0;i<sc.length;i++){
                if(Integer.parseInt(sc[i])<Integer.parseInt(sd[i])){
                    b=true;
                    break;
                }
            }
            if(b) status=EventStatus.over;
            else if(status==EventStatus.unknown) status=EventStatus.asPlanned;
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException ignored){
            status=EventStatus.unknown;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        corecctStatus();
        return date;
    }

    public void setDate(String date) {
        if(!date.equals(this.date)){
            this.date = date;
            status=EventStatus.moved;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventStatus getStatus() {
        corecctStatus();
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int[] getParticipants() {
        return participants;
    }

    public void setParticipants(int[] participants) {
        this.participants = participants;
    }

}
