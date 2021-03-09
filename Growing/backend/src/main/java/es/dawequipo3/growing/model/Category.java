package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    private String name;

    private String description;
    private String icon;
    private String color;
    public Category(){}

    //Basic relationships
    /*@OneToMany(cascade = CascadeType.ALL)
    private List<Tree> tree;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Plan> plans;*/

    public Category(String name, String description, String icon, String color) {
        super();
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.color = color;
       /* this.tree = new ArrayList<>();
        this.plans = new ArrayList<>();*/
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
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
