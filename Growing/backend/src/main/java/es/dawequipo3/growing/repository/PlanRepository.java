package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,String> {
    List<Plan> findPlansByCategory_Name(String name);
    Optional<Plan> findPlansByName(String name);

    @Query(value = "select * from plan p join user_liked_plans u on p.name = u.liked_plans_name and u.liked_by_email= :user and p.category_name= :category", nativeQuery = true)
    List<Plan> getPlanByCategoryAndAndLikedUser(String category, String user);

    @Query(value = "select count(*) from user_liked_plans where liked_plans_name= :plan and liked_by_email= :user", nativeQuery = true)
    Long existsLiked(String plan, String user);



    @Query(value = "select * from plan where plan.category_name in ?1", nativeQuery = true)
    public List<Plan> getLikedPlanFromCategory(List<String> categoryList);
}
