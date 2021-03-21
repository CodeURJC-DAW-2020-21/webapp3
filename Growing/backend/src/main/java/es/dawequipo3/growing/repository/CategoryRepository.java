package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.Category;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import es.dawequipo3.growing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,String> {

    Optional<Category> findCategoryByName(String name);

}
