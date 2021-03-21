package es.dawequipo3.growing.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;


@Embeddable
public class CompletedPlanPK implements Serializable {

    @Column
    private String user_PK;

    @Column
    private String planPK;

    @Column
    private Long datePK;

    public CompletedPlanPK() {
    }

    public CompletedPlanPK(String user_PK, String planPK) {
        this.user_PK = user_PK;
        this.planPK = planPK;
        this.datePK = Calendar.getInstance().getTimeInMillis();
    }

    public String getUser_PK() {
        return user_PK;
    }

    public void setUser_PK(String user) {
        this.user_PK = user;
    }

    public String getPlanPK() {
        return planPK;
    }

    public void setPlanPK(String category) {
        this.planPK = category;
    }

    public long getDatePK() {
        return datePK;
    }

    public void setDatePK(long date) {
        this.datePK = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompletedPlanPK that = (CompletedPlanPK) o;
        return datePK.equals(that.datePK) && user_PK.equals(that.user_PK) && planPK.equals(that.planPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_PK, planPK, datePK);
    }
}
