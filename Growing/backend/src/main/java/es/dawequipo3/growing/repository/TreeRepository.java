package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree,Integer> {
}
