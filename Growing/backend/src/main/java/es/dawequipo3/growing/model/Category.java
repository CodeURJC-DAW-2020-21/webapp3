package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.List;



@Entity
public class Category {

    @Id
    private String name;
    private String des;
    private String icon;
    private String color;

    @OneToMany(mappedBy = "category")
    private List<Plan> plans;

    public Category() {
    }

    public Category(String name, String des, String icon, String color, List<Plan> plans) {
        super();
        this.name = name;
        this.des = des;
        this.icon = icon;
        this.color = color;
        this.plans = plans;
    }

    public Category(String name, String description, String icon, String color) {
        super();
        this.name = name;
        this.des = description;
        this.icon = icon;
        this.color = color;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
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
