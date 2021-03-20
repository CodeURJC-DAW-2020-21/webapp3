package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.text.SimpleDateFormat;
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
        this.user = user;
        this.plan = plan;
        this.date = Calendar.getInstance().getTimeInMillis();
    }

    public Completed_plan() {

    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getPlanName() {
        return plan.getName();
    }

    public String getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(this.date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        return format.format(date.getTime());
    }


}
