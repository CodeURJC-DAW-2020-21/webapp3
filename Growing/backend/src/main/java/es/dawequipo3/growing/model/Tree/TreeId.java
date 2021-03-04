package es.dawequipo3.growing.model.Tree;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class TreeId implements Serializable {
    @Column(name="User")
    private String user;


    @Column(name="Category")
    private String category;


    public TreeId(String user, String category) {
        this.user = user;
        this.category = category;
    }

    public TreeId() {
    }

    public String getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeId treeId = (TreeId) o;

        if (user != null ? !user.equals(treeId.user) : treeId.user != null) return false;
        return category != null ? category.equals(treeId.category) : treeId.category == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
