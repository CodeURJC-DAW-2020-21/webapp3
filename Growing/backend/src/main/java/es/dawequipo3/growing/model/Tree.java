package es.dawequipo3.growing.model;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Tree {
    @EmbeddedId
    private TreePK treePK;
    private int height;

    @Column
    private long last_update; //Stored as ms since epoch

    @ManyToOne
    @MapsId("userPK")
    User user;

    @ManyToOne
    @MapsId("categoryPK")
    Category category;

    public Tree(User PKUser, Category PKCategory,int height, long last_update) {
        super();
        this.treePK = new TreePK(PKUser.getEmail(),PKCategory.getName());
        this.height = height;
        this.last_update = last_update;
        this.user=PKUser;
        this.category=PKCategory;
    }

    public Tree() {

    }

    public Tree(User PKUser, Category PKCategory) {
        super();
        this.treePK = new TreePK(PKUser.getEmail(),PKCategory.getName());
        this.height = 13;
        this.last_update = Calendar.getInstance().getTimeInMillis();
        this.user=PKUser;
        this.category=PKCategory;
    }

    public TreePK getTreePK() {
        return treePK;
    }

    public void setTreePK(TreePK treePK) {
        this.treePK = treePK;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getLast_update() {
        return last_update;
    }

    public void setLast_update(long last_update) {
        this.last_update = last_update;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

