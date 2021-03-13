package es.dawequipo3.growing.model;



import javax.persistence.*;
import java.util.List;

@Table(name = "User")
@Entity
public class User {
    @Id
    @Column(name = "email")
    private String email;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(mappedBy = "likedBy")
    private List<Plan> likedPlans;


    public User() {}

    public User(String email, String username, String name, String surname, String password, String confirmPassword) {
        super();
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User(String email, String username, String name, String surname, String password) {
        super();
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}