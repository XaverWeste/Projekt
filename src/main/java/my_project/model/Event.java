package my_project.model;

public class Event extends Viewable{

    public enum EventStatus{asPlanned, moved,canceled,unknown}

    private int id;
    private String date,name;
    private Event.EventStatus status;
    private String description;
    private int[] participants;

    public Event(int id, String name, String date, Event.EventStatus status, String description, int[] participants){
        this.id=id;
        this.name=name;
        this.date=date;
        this.status=status;
        this.description=description;
        this.participants=participants;
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
        if(t.equals(Event.EventStatus.canceled)) return 3;
        else return 5;
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
        this.date = date;
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

    public int[] getParticipants() {
        return participants;
    }

    public void setParticipants(int[] participants) {
        this.participants = participants;
    }

}
