package es.dawequipo3.growing.model.Tree;


import es.dawequipo3.growing.model.Category.Category;
import es.dawequipo3.growing.model.User.User;

import javax.persistence.*;


@Entity
public class Tree {
    @Id
    @GeneratedValue()
    private int id;
    private int Height;
    private int last_update; //Stored as ms since epoch


    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getLast_update() {
        return last_update;
    }

    public void setLast_update(int last_update) {
        this.last_update = last_update;
    }
}
