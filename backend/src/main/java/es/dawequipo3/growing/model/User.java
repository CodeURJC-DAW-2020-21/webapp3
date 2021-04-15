package es.dawequipo3.growing.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {


    public interface Basic {
    }

    public interface Categories {
    }

    public interface Plans {
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Tree> trees;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Completed_plan> completed_plans;

    @JsonView(Basic.class)
    @Id
    private String email;

    @JsonView(Basic.class)
    @Column(nullable = false, unique = true)
    private String username;

    @JsonView(Basic.class)
    @Column(nullable = false)
    private String name;

    @JsonView(Basic.class)
    @Column(nullable = false)
    private String surname;

    @JsonIgnore
    @Column(nullable = false)
    private String encodedPassword;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    @JsonIgnore
    @Transient
    private String confirmEncodedPassword;

    @JsonView(Basic.class)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @JsonView(Plans.class)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_liked_plans",
            joinColumns = @JoinColumn(name = "liked_by_email"),
            inverseJoinColumns = @JoinColumn(name = "liked_plans_name"))
    private List<Plan> likedPlans;

    @JsonView(Categories.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_user_liked",
            joinColumns = @JoinColumn(name = "user_liked_email"),
            inverseJoinColumns = @JoinColumn(name = "liked_categories_name"))
    private List<Category> userFavoritesCategory;

    public User() {
    }

    //This constructor is for creating sample users
    public User(String email, String username, String name, String surname, String encodedPassword, String... roles) {
        super();
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
        this.trees = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("/static/images/defaultProfileImage.png");
            setImageFile(BlobProxy.generateProxy(resource.getInputStream()
                    , resource.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FavoriteCategory(Category category) {
        this.userFavoritesCategory.add(category);
    }

    public boolean CategoryNameInTrees(Category category) {
        String name = category.getName();
        for (Tree tree : this.trees) {
            if (tree.getCategory().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public void setPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public List<Plan> getLikedPlans() {
        return likedPlans;
    }

    public void setLikedPlans(List<Plan> likedPlans) {
        this.likedPlans = likedPlans;
    }

    public List<Category> getUserFavoritesCategory() {
        return userFavoritesCategory;
    }

    public void setUserFavoritesCategory(List<Category> fav_categories) {
        this.userFavoritesCategory = fav_categories;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public void setTrees(List<Tree> trees) {
        this.trees = trees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getConfirmEncodedPassword() {
        return confirmEncodedPassword;
    }

    public void setConfirmEncodedPassword(String confirmEncodedPassword) {
        this.confirmEncodedPassword = confirmEncodedPassword;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }

    public void addLikedPlan(Plan plan) {
        this.likedPlans.add(plan);
    }
}