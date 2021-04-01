package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class RESTUser {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompletedPlanService completedPlanService;

    interface UserDetails extends User.Basico {
    }

    interface CompletedPlanDetails extends Completed_plan.Basico {
    }

    @JsonView(RESTUser.UserDetails.class)
    @GetMapping("/profile")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(RESTUser.UserDetails.class)
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestParam String email, @RequestParam String username,
                                           @RequestParam String name, @RequestParam String surname,
                                           @RequestParam String encodedPassword, @RequestParam String confirmEncodedPassword) {

        Optional<User> op = userService.findUserByEmail(email);
        Optional<User> op1 = userService.findUserByName(username);
        if (op.isEmpty() && op1.isEmpty() && encodedPassword.equals(confirmEncodedPassword)) {
            User user = new User(email, username, name, surname, passwordEncoder.encode(encodedPassword), "USER");
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @JsonView(RESTUser.UserDetails.class)
    @PutMapping("/edited")
    public ResponseEntity<User> editUser(@RequestParam String username, @RequestParam String name,
                                         @RequestParam String surname, @RequestParam String encodedPassword, @RequestParam String confirmEncodedPassword, HttpServletRequest request) {

        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            if (!username.isBlank()) {
                user.setUsername(username);
            }
            if (!name.isBlank()) {
                user.setName(name);
            }
            if (!surname.isBlank()) {
                user.setSurname(surname);
            }
            if (!encodedPassword.isBlank() && !confirmEncodedPassword.isBlank() && encodedPassword.equals(confirmEncodedPassword)) {
                user.setEncodedPassword(passwordEncoder.encode(encodedPassword));
            }
            userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(RESTPlan.CompletedPlanDetails.class)
    @GetMapping("/completedPlans")
    public ResponseEntity<List> getCompletedTasks(@RequestParam String email, HttpServletRequest request) {
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            List<Completed_plan> completed_plan = completedPlanService.getCompletedPlanPageByEmailSortedByDate(email);
            return new ResponseEntity<>(completed_plan, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
