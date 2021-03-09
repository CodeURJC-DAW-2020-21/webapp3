package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Plan {

    @Id
    private String Name;
    private String Description;
    private int difficulty;

    @ManyToOne
    private Category category;

    public Plan(){}

    public Plan(String name, String description, int difficulty) {
        Name = name;
        Description = description;
        this.difficulty = difficulty;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
