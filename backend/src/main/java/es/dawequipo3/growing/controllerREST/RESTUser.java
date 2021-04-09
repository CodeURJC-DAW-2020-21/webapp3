package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/users")
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


    interface UserDetails extends User.Basic {
    }

    interface CompletedPlanDetails extends Completed_plan.Basic, Completed_plan.Completed, User.Basic, Plan.Basic {}
    interface CompletedPlanUser extends Completed_plan.Completed, Plan.Basic{}

    interface Charts extends ChartData.Basico {
    }

    @Operation(summary = "Get user profile by the email")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the user profile",
                    content = {@Content(
                            schema = @Schema(implementation = User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            )
    })

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

    @Operation(summary = "Create a new user")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Account created successfully",
                    content = {@Content(
                            schema = @Schema(implementation = User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "There is already a user with this email or username",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Only unregistered users can create a new one",
                    content = @Content
            )
    })

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
        } else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Operation(summary = "Edit user profile")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Changes made successfully",
                    content = {@Content(
                            schema = @Schema(implementation = User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            )
    })

    @JsonView(RESTUser.UserDetails.class)
    @PutMapping("/profile/edited")
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

    @Operation(summary = "Show completed plans by email")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all plans completed by the user",
                    content = {@Content(
                            schema = @Schema(implementation = List.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to admin account",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })

    @JsonView(RESTUser.CompletedPlanUser.class)
    @GetMapping("/completedPlans")
    public ResponseEntity<List<Completed_plan>> getCompletedTasksByUser(@RequestParam String email) {
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            List<Completed_plan> completed_plan = completedPlanService.getCompletedPlanPageByEmailSortedByDate(email);
            return new ResponseEntity<>(completed_plan, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Show all completed plans by users")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all plans completed by all the users",
                    content = {@Content(
                            schema = @Schema(implementation = List.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to admin account",
                    content = @Content
            )
    })

    @JsonView(RESTUser.CompletedPlanDetails.class)
    @GetMapping("/completedPlans/")
    public ResponseEntity<List<Completed_plan>> getCompletedTasks(HttpServletRequest request) {
        return new ResponseEntity<>(completedPlanService.getAllCompletedPlans(request), HttpStatus.OK);
    }

    @Operation(summary = "Show the user progress with the tree height")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all categories with their height",
                    content = {@Content(
                            schema = @Schema(implementation = ArrayList.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            )
    })

    //BarChart
    @JsonView(RESTUser.Charts.class)
    @GetMapping("/profile/treeHeight")
    public ResponseEntity<ArrayList<ChartData>> getHeight(HttpServletRequest request) {
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

    @Operation(summary = "Show the user progress with the number of favourites plans per category")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all categories with their number of favourite plans",
                    content = {@Content(
                            schema = @Schema(implementation = ArrayList.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            )
    })

    //DoughnutChart
    @JsonView(RESTUser.Charts.class)
    @GetMapping("/profile/favPlans")
    public ResponseEntity<ArrayList<ChartData>> getFavPlans(HttpServletRequest request) {
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

    @Operation(summary = "Show the user progress with the number of finished plans per category")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all categories with their number of finished plans",
                    content = {@Content(
                            schema = @Schema(implementation = ArrayList.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            )
    })

    //RadarChart
    @JsonView(RESTUser.Charts.class)
    @GetMapping("/profile/finishedPlans")
    public ResponseEntity<ArrayList<ChartData>> getFinishedPlans(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            ArrayList<ChartData> list = new ArrayList<>();
            for (Category category : categoryService.findAll()) {
                list.add(new ChartData(email, category.getName(), category.getColor(), completedPlanService.countTasksDoneByUserAndCategory(email, category.getName())));
            }
            return new ResponseEntity<> (list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
