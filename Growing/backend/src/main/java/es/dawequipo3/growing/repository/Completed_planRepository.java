package es.dawequipo3.growing.repository;


import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Completed_planRepository extends JpaRepository<Completed_plan, Integer> {
    List<Completed_plan> getCompleted_planByUserOrderByDateDesc(User user, Pageable pageable);
}
