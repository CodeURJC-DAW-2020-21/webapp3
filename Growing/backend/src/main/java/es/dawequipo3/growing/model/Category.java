package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
public class Category {

    @Id
    private String name;
    private String des;
    private String icon;
    private String color;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) && Objects.equals(des, category.des) && Objects.equals(icon, category.icon) && Objects.equals(color, category.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, des, icon, color);
    }
}
