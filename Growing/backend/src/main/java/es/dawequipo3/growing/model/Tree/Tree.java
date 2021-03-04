package es.dawequipo3.growing.model.Tree;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Tree {
    @EmbeddedId
    private TreeId Id;
    private int Height;
    private int last_update; //Stored as ms since epoch
}
