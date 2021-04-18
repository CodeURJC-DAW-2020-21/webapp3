package es.dawequipo3.growing.controllerREST;


import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.controllerREST.requestBody.EditPlanRequest;
import es.dawequipo3.growing.controllerREST.requestBody.PlanCreateRequest;
import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.CompletedPlanService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/api/plans")
public class RESTPlan {

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CompletedPlanService completedPlanService;

    interface PlanDetails extends Plan.Basic, Category.Basic {
    }

    interface CompletedPlanDetails extends Completed_plan.Basic, Completed_plan.Completed, Plan.Basic, User.Basic {
    }

    interface UserRegisteredPlanDetails extends Plan.Registered, Plan.Basic, User.Basic {
    }

    @Operation(summary = "Get all the plans")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the plans correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            )
    })

    @JsonView(PlanDetails.class)
    @GetMapping("/")
    public ResponseEntity<Collection<Plan>> getPlans() {
        return ResponseEntity.ok(planService.findAll());

    }

    @Operation(summary = "Get all the plans of a given category by category's name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the plans correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            )
    })
    @JsonView(PlanDetails.class)
    @GetMapping("/category")
    public ResponseEntity<Collection<Plan>> getPlansbyCategoryName(@RequestParam String categoryName) {
        Optional<Category> op = categoryService.findByName(categoryName);
        if (op.isPresent()) {
            return ResponseEntity.ok(planService.findPlansByCategory(categoryName));
        } else return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get the plan list of the indicated explore's page")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the plans correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Page without content",
                    content = @Content
            )
    })
    @JsonView(PlanDetails.class)
    @GetMapping("/explore")
    public ResponseEntity<Page<Plan>> getPlansPage(@RequestParam(defaultValue = "0", required = false) int page) {
        Page<Plan> plans = planService.findAll(page);
        if (page < 0 || page > plans.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return ResponseEntity.ok(planService.findAll(page));
    }

    @Operation(summary = "Create a new plan as an administrator")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created the plan correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to admin account",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "There is not a category with this name",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "There is already a plan with the name given by parameter",
                    content = @Content
            )
    })
    @JsonView(PlanDetails.class)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Plan> createPlan(@RequestParam String category, @RequestBody PlanCreateRequest planRequest) {
        String planName = planRequest.getPlanName();
        String abv = planRequest.getAbv();
        String description = planRequest.getDescription();
        int difficulty = planRequest.getDifficulty();
        try {
            Plan plan = planService.createPlan(planName, category, abv, description, difficulty);
            if (plan != null) {
                URI location = URI.create("https://localhost:8443/api/plans?planName=".concat(planName.replaceAll(" ", "%20")));
                return ResponseEntity.created(location).body(plan);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Complete the plan by name indicated as the logged user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Completed the plan correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })
    @JsonView(PlanDetails.class)
    @PostMapping("/done")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Plan> completePlan(@RequestParam String planName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()) {
            Plan plan = op.get();
            planService.saveCompletedPlan(user, plan);
            return ResponseEntity.ok(plan);
        } else return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Get all the completed plans")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plans found correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to admin user",
                    content = @Content
            )
    })
    @JsonView(CompletedPlanDetails.class)
    @GetMapping("/completedPlans")
    public ResponseEntity<Collection<Completed_plan>> getAllCompletedPlan() {
        return ResponseEntity.ok(completedPlanService.findall());
    }

    @Operation(summary = "Remove a completed plan of a given user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Completed plan removed correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to admin user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Completed plan not found",
                    content = @Content
            )
    })
    @JsonView(CompletedPlanDetails.class)
    @DeleteMapping("/completedPlans")
    public ResponseEntity<Completed_plan> removeCompletedPlanbyUser(@RequestParam String planName, @RequestParam String email , @RequestParam String date) {
        try {
            Completed_plan completed_plan = planService.removeCompletedPlan(email, planName, date);
            return ResponseEntity.ok(completed_plan);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get the information of a plan by the name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan found correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })
    @JsonView(PlanDetails.class)
    @GetMapping("")
    public ResponseEntity<Plan> getPlan(@RequestParam String planName) {
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()) {
            Plan plan = op.get();
            return ResponseEntity.ok(plan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Like a plan given the abbreviation as the logged user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan liked correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })
    @JsonView(UserRegisteredPlanDetails.class)
    @PutMapping("/favA")
    public ResponseEntity<Plan> likePlan(@RequestParam String abbrev, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            try {
                Plan plan = planService.likePlanAbbr(abbrev, request);
                return ResponseEntity.ok(plan);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @Operation(summary = "Remove the like of a plan given the abbreviation as the logged user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Like removed correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })
    @JsonView(UserRegisteredPlanDetails.class)
    @PutMapping("/notFavA")
    public ResponseEntity<Plan> dislikePlan(@RequestParam String abbrev, HttpServletRequest request) {

        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            try {
                Plan plan = planService.dislikePlanAbbr(abbrev, request);
                return ResponseEntity.ok(plan);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Like a plan given its name as the logged user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan liked correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })

    @JsonView(UserRegisteredPlanDetails.class)
    @PutMapping("/favN")
    public ResponseEntity<Plan> likePlanN(@RequestParam String planName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            try {
                Plan plan = planService.likePlanName(planName, request);
                return ResponseEntity.ok(plan);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @Operation(summary = "Remove the like of a plan given its name as the logged user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Like removed correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to registered user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })
    @JsonView(UserRegisteredPlanDetails.class)
    @PutMapping("/notFavN")
    public ResponseEntity<Plan> dislikePlanN(@RequestParam String planName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            try {
                Plan plan = planService.dislikePlanName(planName, request);
                return ResponseEntity.ok(plan);
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Edit the information of a plan as an administrator")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Edited the plan correctly",
                    content = {@Content(
                            schema = @Schema(implementation = Plan.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Only access to admin account",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plan not found",
                    content = @Content
            )
    })
    @JsonView(PlanDetails.class)
    @PutMapping("")
    public ResponseEntity<Plan> editPlan(@RequestParam String planName, @RequestBody EditPlanRequest editPlanRequest) {
        String newDescription=editPlanRequest.getNewDescription();
        String abv=editPlanRequest.getAbv();
        int difficulty=editPlanRequest.getDifficulty();
        try{
            Plan plan=planService.editPlan(planName,newDescription,abv,difficulty);
            return ResponseEntity.ok(plan);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
