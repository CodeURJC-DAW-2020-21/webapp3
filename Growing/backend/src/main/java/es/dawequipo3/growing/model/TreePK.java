package es.dawequipo3.growing.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class TreePK implements Serializable {

    @Column
    private String user;

    @Column
    private String category;

    public TreePK(String user, String category) {
        this.user = user;
        this.category = category;
    }

    public TreePK() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
