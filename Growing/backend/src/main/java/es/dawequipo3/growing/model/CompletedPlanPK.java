package es.dawequipo3.growing.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;


@Embeddable
public class CompletedPlanPK implements Serializable {

    @Column
    private String user_Pk;

    @Column
    private String planPK;

    @Column
    private Long datePK;

    public CompletedPlanPK() {
    }

    public CompletedPlanPK(String user_Pk, String planPK) {
        this.user_Pk = user_Pk;
        this.planPK = planPK;
        this.datePK = Calendar.getInstance().getTimeInMillis();
    }

    public String getUser_Pk() {
        return user_Pk;
    }

    public void setUser_Pk(String user) {
        this.user_Pk = user;
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
        return datePK == that.datePK && user_Pk.equals(that.user_Pk) && planPK.equals(that.planPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_Pk, planPK, datePK);
    }
}
