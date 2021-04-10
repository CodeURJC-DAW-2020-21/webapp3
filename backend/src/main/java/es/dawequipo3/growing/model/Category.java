package es.dawequipo3.growing.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Category {

    public interface Basic {}
    public interface Trees {}
    public interface Plans {}

    @JsonView(Trees.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    List<Tree> trees;

    @JsonView(Basic.class)
    @Id
    private String name;
    @JsonView(Basic.class)
    private String des;

    @Lob
    @JsonIgnore
    private Blob icon;

    @JsonView(Basic.class)
    private String color;

    @JsonView(Basic.class)
    @Transient
    private boolean likedByUser;

    @JsonView(Plans.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Plan> plans;

    @JsonIgnore
    @ManyToMany(mappedBy = "userFavoritesCategory")
    private List<User> userFavoritesCategory;

    public Category() {
    }

    public Category(String name, String description, String color) {
        super();
        this.name = name;
        this.des = description;
        this.color = color;
        this.plans = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("/static/images/defaultCategoryIcon.png");
            setIcon(BlobProxy.generateProxy(resource.getInputStream()
                    , resource.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Category(String name, String description, MultipartFile icon, String color) throws IOException {
        super();
        this.name = name;
        this.des = description;
        this.color = color;
        this.plans = new ArrayList<>();
        this.icon = BlobProxy.generateProxy(icon.getInputStream(), icon.getSize());
    }

    public Category(String name, String description, String icon, String color) {
        super();
        this.name = name;
        this.des = description;
        this.color = color;
        this.plans = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("/static/images/" + icon + ".png");
            setIcon(BlobProxy.generateProxy(resource.getInputStream()
                    , resource.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Blob getIcon() {
        return icon;
    }

    public void setIcon(Blob icon) {
        this.icon = icon;
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

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
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