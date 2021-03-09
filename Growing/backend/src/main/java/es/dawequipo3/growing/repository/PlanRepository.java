package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan,String> {
    List<Plan> findPlansByCategory_Name(String name);
}
