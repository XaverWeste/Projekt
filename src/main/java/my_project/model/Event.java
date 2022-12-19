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

    public void corecctStatus(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd:hh:mm");
        String ct=sdf.format(c.getTime());
        //TODO fix
        try{
            boolean b=false;
            String[] sd=date.split(":");
            String[] sc=ct.split(":");
            if(sd.length==sc.length){
                if(Integer.parseInt(sd[0])>=Integer.parseInt(sc[0])){
                    if(Integer.parseInt(sd[1])>=Integer.parseInt(sc[1])){
                        if(Integer.parseInt(sd[1])==Integer.parseInt(sc[1])&&Integer.parseInt(sd[2])>=Integer.parseInt(sc[2])){
                            if(Integer.parseInt(sd[2])==Integer.parseInt(sc[2])){
                                b=Double.parseDouble(sd[3]+"."+sd[4])>Double.parseDouble(sc[3]+"."+sc[4]);
                            }
                        }else b=true;
                    }else b=true;
                }else b=true;
                if (status != EventStatus.canceled) {
                    if (b) status = EventStatus.over;
                    else if (status != EventStatus.moved) status = EventStatus.asPlanned;
                }
            }else status=EventStatus.unknown;
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
