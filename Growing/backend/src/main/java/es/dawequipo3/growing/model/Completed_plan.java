package es.dawequipo3.growing.model;



import javax.persistence.*;

@Entity
public class Completed_plan {
    @Id
    @GeneratedValue
    private int Id;
    private int date; //ms since epoch

}