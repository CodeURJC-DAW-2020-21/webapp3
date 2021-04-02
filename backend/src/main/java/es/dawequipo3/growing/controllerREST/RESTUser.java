package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlanService planService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private Completed_planRepository completed_planRepository;

    interface UserDetails extends User.Basico {
    }

    interface CompletedPlanDetails extends Completed_plan.Basico {
    }

    interface Charts extends ChartData.Basico {
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

    @JsonView(RESTUser.CompletedPlanDetails.class)
    @GetMapping("/completedPlans")
    public ResponseEntity<List> getCompletedTasks(@RequestParam String email, HttpServletRequest request) {
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            List<Completed_plan> completed_plan = completedPlanService.getCompletedPlanPageByEmailSortedByDate(email);
            return new ResponseEntity<>(completed_plan, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //BarChart
    @JsonView(RESTUser.Charts.class)
    @GetMapping("/treeHeight")
    public ResponseEntity<ArrayList> getHeight(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            ArrayList<ChartData> list = new ArrayList<>();
            for (Category category : categoryService.findAll()) {
                list.add(new ChartData(email, category.getName(), category.getColor(), treeService.findTree(email, category.getName()).orElseThrow().getHeight()));
            }
            return new ResponseEntity<> (list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DoughnutChart
    @JsonView(RESTUser.Charts.class)
    @GetMapping("/favPlans")
    public ResponseEntity<ArrayList> getFavPlans(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            ArrayList<ChartData> list = new ArrayList<>();
            for (Category category : categoryService.findAll()) {
                list.add(new ChartData(email, category.getName(), category.getColor(), planService.likedplans(email, category.getName()).size()));
            }
            return new ResponseEntity<> (list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //RadarChart
    @JsonView(RESTUser.Charts.class)
    @GetMapping("/finishedPlans")
    public ResponseEntity<ArrayList> getFinishedPlans(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            ArrayList<ChartData> list = new ArrayList<>();
            for (Category category : categoryService.findAll()) {
                list.add(new ChartData(email, category.getName(), category.getColor(), completed_planRepository.countTasksDoneByUserAndCategory(email, category.getName())));
            }
            return new ResponseEntity<> (list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
