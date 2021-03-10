package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.Date;

@Table(name = "Tree")
@Entity
public class Tree {
    @EmbeddedId
    private TreePK treePK;
    private int height;
    @Column(insertable = false, updatable = false)
    private Date last_update; //Stored as ms since epoch

    public Tree(User PKUser, Category PKCategory,int height, Date last_update) {
        super();
        this.treePK = new TreePK(PKUser.getEmail(),PKCategory.getName());
        this.height = height;
        this.last_update = last_update;
    }

    public Tree() {

    }

    public Tree(String email, String name) {
        super();
        this.treePK = new TreePK(email,name);
        this.height = 0;
        this.last_update = new Date();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
}
