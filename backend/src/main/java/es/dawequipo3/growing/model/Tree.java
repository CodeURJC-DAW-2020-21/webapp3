package es.dawequipo3.growing.model;




import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Tree {

    public interface Basic {}
    public interface Categories {}

    @ManyToOne
    @MapsId("userPK")
    User user;

    @ManyToOne
    @MapsId("categoryPK")
    Category category;

    @JsonView(Basic.class)
    @EmbeddedId
    private TreePK treePK;

    @JsonView(Basic.class)
    private int height;

    @Column
    private long last_update; //Stored as ms since epoch


    public Tree(User PKUser, Category PKCategory, int height, long last_update) {
        super();
        this.treePK = new TreePK(PKUser.getEmail(), PKCategory.getName());
        this.height = height;
        this.last_update = last_update;
        this.user = PKUser;
        this.category = PKCategory;
    }

    public Tree() {
    }


    public Tree(User PKUser, Category PKCategory) {
        super();
        this.treePK = new TreePK(PKUser.getEmail(), PKCategory.getName());
        this.height = 0;
        this.last_update = Calendar.getInstance().getTimeInMillis();
        this.user = PKUser;
        this.category = PKCategory;
    }

    public String getImagePath() {
        String path;
        if (this.height < 10) {
            path = "/assets/img/progress/fase1.png";
        } else if (this.height < 30) {
            path = "/assets/img/progress/fase2.png";
        } else if (this.height < 50) {
            path = "/assets/img/progress/fase3.png";
        } else if (this.height < 80) {
            path = "/assets/img/progress/fase4.png";
        } else {
            path = "/assets/img/progress/fase5.png";
        }
        return path;
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

    public String getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(this.last_update);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        return format.format(date.getTime());
    }
}
