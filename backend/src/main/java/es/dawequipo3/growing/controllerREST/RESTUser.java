package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.controllerREST.requestBody.UserRequest;
import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

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

    interface CompletedPlanDetails extends Completed_plan.Basic, Completed_plan.Completed, User.Basic, Plan.Basic {
    }

    interface CompletedPlanUser extends Completed_plan.Completed, Plan.Basic {
    }

    interface Charts extends ChartData.Basico {
    }

    @Operation(summary = "Create a new user")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Account created successfully",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDetails.class)
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
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        String email = userRequest.getEmail();
        String username = userRequest.getUsername();
        String name = userRequest.getName();
        String surname = userRequest.getSurname();
        String encodedPassword = userRequest.getEncodedPassword();
        String confirmEncodedPassword = userRequest.getConfirmEncodedPassword();

        Optional<User> op = userService.findUserByEmail(email);
        Optional<User> op1 = userService.findUserByName(username);
        if (op.isEmpty() && op1.isEmpty() && encodedPassword.equals(confirmEncodedPassword)) {
            User user = new User(email, username, name, surname, passwordEncoder.encode(encodedPassword), "USER");
            userService.save(user);
            URI location = URI.create("https://localhost:8443/api/users/info?email=" + email);
            return ResponseEntity.created(location).body(user);
        } else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    // Get users logged

    @Operation(summary = "Get all users")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all the users",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to an admin user",
                    content = @Content
            )
    })
    @JsonView(RESTUser.UserDetails.class)
    @GetMapping("")
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Get all users")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all the users",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to an admin user",
                    content = @Content
            )
    })

    // Get user logged searched by email
    @JsonView(RESTUser.UserDetails.class)
    @GetMapping("/info")
    public ResponseEntity<User> getUserEmail(@RequestParam String email, HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            if (request.isUserInRole("ADMIN") || request.getUserPrincipal().getName().equals(email))
                return userService.returnUser(email);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // OPERATIONS WITH THE CURRENT USER

    // Get principal information
    @Operation(summary = "Get user logged profile")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the current user profile",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered users",
                    content = @Content
            )
    })
    @JsonView(RESTUser.UserDetails.class)
    @GetMapping("/profile")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return userService.returnUser(email);
    }

    // Get profile image
    @Operation(summary = "Returns the current user profile image")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category's icon retrieved correctly",
                    content = {@Content(
                            mediaType = "image/*"
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Image not found",
                    content = @Content
            ),
    })
    @GetMapping("/profile/image")
    public ResponseEntity<Object> getImage(HttpServletRequest request) throws SQLException {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            if (user.getImageFile() != null) {
                Resource file = new InputStreamResource(user.getImageFile().getBinaryStream());
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(user.getImageFile().length()).body(file);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Profile editing
    @Operation(summary = "Edit user profile")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Changes made successfully",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            )
    })

    @JsonView(RESTUser.UserDetails.class)
    @PutMapping("/profile")
    public ResponseEntity<User> editUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {

        String username = userRequest.getUsername();
        String name = userRequest.getName();
        String surname = userRequest.getSurname();
        String encodedPassword = userRequest.getEncodedPassword();
        String confirmEncodedPassword = userRequest.getConfirmEncodedPassword();
        String email = request.getUserPrincipal().getName();

        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            if (!username.isBlank() && userService.findUserByName(username).isEmpty()) {
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

    //Changes the profile image
    @Operation(summary = "Changes the current user profile image with a new one indicated by the user")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User's profile icon changed correctly",
                    content = {@Content(
                            mediaType = "image/*",
                            schema = @Schema(implementation = UserDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permission error, only access to registered users",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Image not found",
                    content = @Content
            ),
    })
    @JsonView(User.Basic.class)
    @PutMapping("/profile/image")
    public ResponseEntity<User> uploadImage(HttpServletRequest request, @RequestParam MultipartFile imageFile) throws SQLException, IOException {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            if (imageFile != null) {
                if (!imageFile.isEmpty()) {
                    user.setImageFile(BlobProxy.generateProxy(
                            imageFile.getInputStream(), imageFile.getSize()));
                }
            }
            userService.update(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }



    @Operation(summary = "Show completed plans by email")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all plans completed by the user",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RESTUser.CompletedPlanUser.class)
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
                            mediaType = "application/json",
                            schema = @Schema(implementation = RESTUser.CompletedPlanDetails.class)
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

    // GETS CHART INFORMATION

    @Operation(summary = "Show the user progress with the tree height")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all categories with their height",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RESTUser.Charts.class)
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
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Show the user progress with the number of favourites plans per category")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all categories with their number of favourite plans",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RESTUser.Charts.class)
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
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Show the user progress with the number of finished plans per category")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all categories with their number of finished plans",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RESTUser.Charts.class)
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
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
