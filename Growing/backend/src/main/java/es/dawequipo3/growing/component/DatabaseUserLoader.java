package es.dawequipo3.growing.component;

import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseUserLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDatabase() {
        // USER
        userRepository.save(new User("user@gmail","user","Evarist","Oh", passwordEncoder.encode("pass"), "USER"));
        // ADMIN
        userRepository.save(new User("admin@gmail","admin","Naomi","Watts", passwordEncoder.encode("pass"), "ADMIN","USER"));

    }
}
