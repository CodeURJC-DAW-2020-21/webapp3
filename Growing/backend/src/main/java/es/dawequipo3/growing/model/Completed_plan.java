package es.dawequipo3.growing.model;



import javax.persistence.*;
import java.util.Calendar;


@Entity
public class Completed_plan {

    @EmbeddedId
    private CompletedPlanPK completedPlanPK;

    @ManyToOne
    @MapsId("user_Pk")
    private User user;

    @ManyToOne
    @MapsId("planPK")
    private Plan plan;

    @Column(updatable = false)
    private long date;

    public Completed_plan(User user, Plan plan) {
        super();
        this.completedPlanPK = new CompletedPlanPK(user.getEmail(), plan.getName());
        this.user=user;
        this.plan=plan;
        this.date=Calendar.getInstance().getTimeInMillis();
    }

    public Completed_plan() {

    }
}
