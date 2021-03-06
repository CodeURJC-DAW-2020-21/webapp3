package es.dawequipo3.growing.model.User;


import es.dawequipo3.growing.model.Category.Category;
import es.dawequipo3.growing.model.Plan.Completed_plan;
import es.dawequipo3.growing.model.Plan.Plan;
import es.dawequipo3.growing.model.Tree.Tree;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    private String email;
    private String username;
    private String password;
    private String photo_url;

    //Basic relationships
    @OneToMany(cascade = CascadeType.ALL)
    private List<Tree> tree;

    //Relational relationships
    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> fav_categorie;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Plan> liked_plans;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Completed_plan> completed_plans;



    public User(String email, String username, String password, String photo_url) {
        super();
        this.email = email;
        this.username = username;
        this.password = password;
        this.photo_url = photo_url;
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
}
