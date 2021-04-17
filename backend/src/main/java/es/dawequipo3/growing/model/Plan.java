package es.dawequipo3.growing.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
public class Plan {

    public interface Basic {}

    public interface Registered {}

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan")
    List<Completed_plan> completed_plans;

    @JsonView(Basic.class)
    @Id
    private String name;

    @JsonView(Basic.class)
    private String description;

    @JsonView(Basic.class)
    private int difficulty;

    @JsonIgnore
    @Column(unique = true)
    private String abv;

    @JsonView(Registered.class)
    @Transient
    private boolean likedUser;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedPlans")
    private List<User> likedPlans;

    protected Plan() {
    }

    public Plan(String name, String description, int difficulty, Category category, String abv) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
        this.abv = abv;
    }

    public Plan(String name, String description, int difficulty, String abv) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.abv = abv;
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

    @JsonIgnore
    public Blob getIcon() {
        return this.category.getIcon();
    }

    @JsonIgnore
    public String getUrlToFlag() {
        if (this.difficulty == 1) {
            return "level1.png";
        } else if (this.difficulty == 2) {
            return "level2.png";
        } else {
            return "level3.png";
        }
    }

    @JsonView(Basic.class)
    public String getCategoryName(){
        return this.category.getName();
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

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }
}
