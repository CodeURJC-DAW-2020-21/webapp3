package es.dawequipo3.growing.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;


@Embeddable
public class CompletedPlanPK implements Serializable {

    @Column
    private String user;

    @Column
    private String category;

    @Column(insertable = false, updatable = false)
    private Date date;

    public CompletedPlanPK() {
    }

    public CompletedPlanPK(String user, String category) {
        this.user = user;
        this.category = category;
        this.date = new Date();
    }

}
