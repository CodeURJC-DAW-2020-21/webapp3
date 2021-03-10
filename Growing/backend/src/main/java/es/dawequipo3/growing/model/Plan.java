package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.List;

@Table(name = "Plan")
@Entity
public class Plan {


    @Id
    private String name;
    private String description;
    private int difficulty;

    @ManyToOne
    private Category category;

    @ManyToMany
    private List<User> likedBy;


    protected Plan(){}

    public Plan(String name, String description, int difficulty, Category category) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
    }

    public Plan(String name, String description, int difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
