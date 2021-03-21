package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.TreePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree,TreePK> {

    @Query(value = "select * from tree where category_name= :category and user_email= :user" ,nativeQuery = true)
    Optional<Tree> findTreeByTreePK(String category, String user);

}
