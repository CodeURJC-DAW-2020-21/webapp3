package es.dawequipo3.growing.model;



import javax.persistence.*;

@Entity
public class Completed_plan {
    @Id
    @GeneratedValue
    private int Id;
    private int date; //ms since epoch


    @ManyToOne
    private User user;

    @ManyToOne
    private Plan plan;

    public Completed_plan(int date, User user, Plan plan) {
        this.date = date;
        this.user = user;
        this.plan = plan;
    }

    public Completed_plan() {

    }
}
