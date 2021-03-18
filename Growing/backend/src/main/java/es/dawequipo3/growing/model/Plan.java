package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Plan {

    @Id
    private String name;
    private String description;
    private int difficulty;
    @Transient
    private boolean likedUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "plan")
    List<Completed_plan> completed_plans;

    @ManyToMany(mappedBy = "likedPlans")
    private List<User> likedPlans;


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

    public boolean isLikedUser() {
        return likedUser;
    }

    public void setLikedUser(boolean likedUser) {
        this.likedUser = likedUser;
    }

    public List<User> getLikedPlans() {
        return likedPlans;
    }

    public void setLikedPlans(List<User> likedPlans) {
        this.likedPlans = likedPlans;
    }


}
