package es.dawequipo3.growing.model.Plan;


import es.dawequipo3.growing.model.Category.Category;

import javax.persistence.*;
import java.util.List;

@Entity
public class Plan {

    @Id
    private String Name;
    private String Description;
    private int difficulty;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Completed_plan> completed_plans;


}
