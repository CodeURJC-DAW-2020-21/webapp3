package es.dawequipo3.growing;

import es.dawequipo3.growing.controller.UserController;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class GrowingApplicationTests extends GrowingApplication {

    @Autowired
    private UserService service;

    @MockBean
    private UserService repository;

    @Test
    public void getUserTest(){
        int numberOfUsers = 2;

        when(repository.findAll()).thenReturn(Stream.of(new User("pepe@byexpample.com","exampleName", "exampleName","password","password"),new User("pepe2@byexpample.com","exampleName2", "exampleName2","exampleName2","password"))
        .collect(Collectors.toList()));

        assertEquals(numberOfUsers,service.findAll().size());
    }

}
