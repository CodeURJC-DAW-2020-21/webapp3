package es.dawequipo3.growing.model.Plan;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plan {

    @Id
    private String Name;
    private String Description;

    private int difficulty;

}
