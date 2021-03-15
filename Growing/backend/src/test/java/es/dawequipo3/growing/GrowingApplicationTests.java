package es.dawequipo3.growing;

import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class GrowingApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserService repository;

    @Test
    public void testSaveNewUser(){
        entityManager.persist(new User("test@byexample.com","JhonDoe01","Jhon", "Doe","contraseña"));

        Optional<User> user = repository.findUserByEmail("test@byexample.com");
        assertThat(user.get().getName()).isEqualTo("JhonDoe01");
    }

    @Test
    public void testListUser(){
        List<User> users = (List<User>) repository.findAll();
        assertThat(users.size()).isGreaterThan(2);
    }

    @Test
    @Rollback(false)
    public void testDeleteUser(){
        Optional<User> user = repository.findUserByEmail("p1@gmail.com");

        repository.deleteUser(user);

        Optional<User> deletedUser = repository.findUserByEmail("p1@gmail.com");

        assertThat(deletedUser).isNull();

    }
}
