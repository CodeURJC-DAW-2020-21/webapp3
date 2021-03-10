package es.dawequipo3.growing.model;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class User {
    @Id
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String photo_url;

    //Basic relationships
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private List<Tree> trees;

    //Relational relationships
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Category> fav_categories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Plan> liked_plans;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Completed_plan> completed_plans;


    public User(String email, String username, String password) {
        super();
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


    public void add_tree(Tree tree) {
        this.trees.add(tree);
    }

    public void add_fav_category(Category category) {
        this.fav_categories.add(category);
    }

    public void add_completed_plan(Completed_plan completed_plan) {
        this.completed_plans.add(completed_plan);
    }


    @OneToMany(mappedBy = "user")
    private Collection<Tree> tree;

    public Collection<Tree> getTree() {
        return tree;
    }


}