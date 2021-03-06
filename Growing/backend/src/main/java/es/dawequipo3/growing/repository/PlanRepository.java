package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan,String> {

}
