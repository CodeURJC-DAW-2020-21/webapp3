package es.dawequipo3.growing.model;



import javax.persistence.*;
import java.util.Date;

@Table(name = "completed_plan")
@Entity
public class Completed_plan {

    @Id
    @GeneratedValue
    private CompletedPlanPK id;


    public Completed_plan(User user, Plan plan) {
        super();
        this.id = new CompletedPlanPK(user.getEmail(), plan.getName());
    }

    public Completed_plan() {

    }
}
