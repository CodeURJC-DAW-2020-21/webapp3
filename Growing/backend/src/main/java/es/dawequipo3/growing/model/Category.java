package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    private String name;

    private String des;
    private String icon;
    private String color;


    //Basic relationships
    @OneToMany(mappedBy = "name")
    private List<Category> trees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "name")
    private List<Plan> plans;

    //Relational relations
    @ManyToMany
    private List<User> fav_by_users;

    public Category() {
    }

    public Category(String name, String description, String icon, String color) {
        super();
        this.name = name;
        this.des = description;
        this.icon = icon;
        this.color = color;
        /* this.tree = new ArrayList<>();*/
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

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
