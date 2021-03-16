package es.dawequipo3.growing.repository;


import es.dawequipo3.growing.model.CompletedPlanPK;
import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Completed_planRepository extends JpaRepository<Completed_plan, Integer> {

    @Query(value = "select count(*) from completed_plan join plan p on p.name = completed_plan.plan_name where user_email= :user and p.category_name= :category", nativeQuery = true)
    int countTasksDoneByUserAndCategory(String user, String category);
}
