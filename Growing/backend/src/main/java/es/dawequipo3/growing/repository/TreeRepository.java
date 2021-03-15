package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.TreePK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree,TreePK> {

    Optional<Tree> findTreeByTreePK(TreePK treePK);

}
