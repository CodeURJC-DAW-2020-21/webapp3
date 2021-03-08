package es.dawequipo3.growing.model;


import javax.persistence.*;


@Entity
public class Tree {
    @Id
    @GeneratedValue()
    private int id;
    private int Height;
    private int last_update; //Stored as ms since epoch


    public Tree(int height, int last_update) {
        Height = height;
        this.last_update = last_update;
    }

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
