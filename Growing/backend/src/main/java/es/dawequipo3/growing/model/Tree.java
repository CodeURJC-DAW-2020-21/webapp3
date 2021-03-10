package es.dawequipo3.growing.model;


import javax.persistence.*;


@Entity
public class Tree {
    @EmbeddedId
    private TreePK treePK;

    //Relations
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne
    private Category category;

    private int height;
    private long last_update; //Stored as ms since epoch

    public Tree(User PKUser, Category PKCategory,int height, long last_update) {
        this.treePK = new TreePK(PKUser.getEmail(),PKCategory.getName());
        this.height = height;
        this.last_update = last_update;
        this.user = PKUser;
        this.category = PKCategory;
    }

    public Tree() {

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
}
