package es.dawequipo3.growing;

import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class GrowingApplicationTests {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void getUserTest(){
        int numberOfUsers = 2;

        when(repository.findAll()).thenReturn(Stream.of(new User("pepe@byexpample.com","exampleName","password","photo"),new User("pepe@byexpample.com","exampleName","password","photo"))
        .collect(Collectors.toList()));

        assertEquals(numberOfUsers,service.getUsers().size());
    }


}
