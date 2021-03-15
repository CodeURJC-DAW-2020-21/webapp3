package es.dawequipo3.growing.repository;

import es.dawequipo3.growing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
