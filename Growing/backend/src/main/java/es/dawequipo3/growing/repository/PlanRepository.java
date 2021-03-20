package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan,String> {
    List<Plan> findPlansByCategory_Name(String name);


    @Query(value = "select * from plan where plan.category_name in ?1", nativeQuery = true)
    public List<Plan> getLikedPlanFromCategory(List<String> categoryList);
}
