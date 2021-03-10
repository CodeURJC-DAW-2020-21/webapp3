package es.dawequipo3.growing.model;


import org.h2.result.SearchRow;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class TreePK implements Serializable {
    @Column(name = "user_email")
    private String user;
    @Column(name = "category_name")
    private String category;

    public TreePK(String user, String category) {
        this.user = user;
        this.category = category;
    }

    public TreePK() {

    }
}
