package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,String> {
    List<Plan> findPlansByCategory_Name(String name);

    Optional<Plan> findByName(String name);
}
