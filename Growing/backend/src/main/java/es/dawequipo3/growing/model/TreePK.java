package es.dawequipo3.growing.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class TreePK implements Serializable {

    @Column
    private String userPK;

    @Column
    private String categoryPK;

    public TreePK(String userPK, String categoryPK) {
        this.userPK = userPK;
        this.categoryPK = categoryPK;
    }

    public TreePK() {

    }

    public String getUserPK() {
        return userPK;
    }

    public void setUserPK(String userPK) {
        this.userPK = userPK;
    }

    public String getCategoryPK() {
        return categoryPK;
    }

    public void setCategoryPK(String categoryPK) {
        this.categoryPK = categoryPK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreePK treePK = (TreePK) o;
        return Objects.equals(userPK, treePK.userPK) && Objects.equals(categoryPK, treePK.categoryPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPK, categoryPK);
    }
}
