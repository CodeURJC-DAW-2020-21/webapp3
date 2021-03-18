package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Category {

    @Id
    private String name;
    private String des;
    private String icon;
    private String color;
    private Date date;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    private List<Plan> plans;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    List<Tree> trees;

    @ManyToMany(mappedBy = "userFavoritesCategory")
    private List<User> userFavoritesCategory;

    public Category() {
    }

    public Category(String name, String des, String icon, String color, List<Plan> plans) {
        super();
        this.name = name;
        this.des = des;
        this.icon = icon;
        this.color = color;
        this.plans = plans;
        this.date = new Date();
    }

    public Category(String name, String description, String icon, String color) {
        super();
        this.name = name;
        this.des = description;
        this.icon = icon;
        this.color = color;
        this.date = new Date();
        this.plans = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return des;
    }

    public void setDescription(String description) {
        this.des = description;
    }

    public String getIcon() {
        return icon;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public void setTrees(List<Tree> trees) {
        this.trees = trees;
    }

    public List<User> getUserFavoritesCategory() {
        return userFavoritesCategory;
    }

    public void setUserFavoritesCategory(List<User> favorited_by) {
        this.userFavoritesCategory = favorited_by;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) { this.date = date;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + des + '\'' +
                ", icon='" + icon + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
