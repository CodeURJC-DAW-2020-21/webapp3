package es.dawequipo3.growing.controllerREST;


import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    interface PlanDetails extends Plan.Basic, Category.Basic {}
    interface CompletedPlanDetails extends Completed_plan.Basic, Plan.Basic, User.Basic{}

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
    public ResponseEntity<Collection<Plan>> getPlansbyCategoryName(@RequestParam String categoryName){
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
    public ResponseEntity<List<Plan>> getPlansPage(@RequestParam (defaultValue = "0", required = false) int page) {
        Page<Plan> plans = planService.findAll(page);
        if (page < 0 || page > plans.getTotalPages()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return ResponseEntity.ok(planService.findAll(page).getContent());
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
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Plan> createPlan(@RequestParam String category, @RequestParam String planName,
                                           @RequestParam String abv, @RequestParam String description,
                                           @RequestParam int difficulty) {

        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isEmpty() || (difficulty > 3 || difficulty < 1)){
            Optional<Category> optionalCategory = categoryService.findByName(category);
            if (optionalCategory.isPresent()){
                Plan plan = new Plan(planName, description, difficulty, optionalCategory.get(), abv);
                planService.save(plan);
                URI location = URI.create("https://localhost:8443/api/plans?planName=".concat(plan.getName().replaceAll(" ", "%20")));
                return ResponseEntity.created(location).body(plan);
            } else return ResponseEntity.notFound().build();
        } else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }



    //TODO MUST DO A METHOD TO SEARCH A COMPLETED PLAN BY USER AND COMPLETED PLAN AND DATE AND BY USER, COMPLETED PLAN AND DATE
    // RETURN ALSO A LOCATION
  
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


    @JsonView(RESTPlan.PlanDetails.class)
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
    @DeleteMapping("/completedPlan/removed")
    public ResponseEntity<Completed_plan> removeCompletedPlanbyUser(@RequestParam String email, @RequestParam String planName, @RequestParam String date, HttpServletRequest request) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        try {
            Date dateObject = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateObject);
            long milisecs = calendar.getTimeInMillis();
            Optional<Plan> optionalPlan = planService.findPlanByName(planName);
            if (optionalPlan.isPresent()) {
                Plan plan = optionalPlan.get();
                Optional<Completed_plan> optionalCompleted_plan = completedPlanService.findCompletedPlan(email, plan, milisecs);
                if (optionalCompleted_plan.isPresent()) {
                    Completed_plan completed_plan = optionalCompleted_plan.get();
                    completedPlanService.deleteCompletedPlan(email, planName, milisecs);
                    return ResponseEntity.ok(completed_plan);
                }
                return ResponseEntity.notFound().build();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
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
    @JsonView(RESTPlan.PlanDetails.class)
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
    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/like")
    public ResponseEntity<Plan> likePlan(@RequestParam String abbrev, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            try{
                Plan opPlan = planService.findPlanByAbbr(abbrev);
                user.getLikedPlans().add(planService.findPlanByAbbr(abbrev));
                planService.findPlanByAbbr(abbrev).setLikedUser(true);
                userService.update(user);
                return ResponseEntity.ok(opPlan);
             }catch (NoSuchElementException e){
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
    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/dislike")
    public ResponseEntity<Plan> dislikePlan(@RequestParam String abbrev, HttpServletRequest request){

        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            try{
                Plan opPlan = planService.findPlanByAbbr(abbrev);
                user.getLikedPlans().remove(planService.findPlanByAbbr(abbrev));
                planService.findPlanByAbbr(abbrev).setLikedUser(false);
                userService.update(user);
                return ResponseEntity.ok(opPlan);
            }catch (NoSuchElementException e){
                return ResponseEntity.notFound().build();
            }
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/likeC")
    public ResponseEntity<Plan> likePlanC(@RequestParam String planName, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        try {
            User user = userService.findUserByEmail(email).orElseThrow();
            Optional<Plan> op = planService.findPlanByName(planName);
            if (op.isPresent()) {
                Plan plan = op.get();
                user.getLikedPlans().add(plan);
                plan.setLikedUser(true);
                userService.update(user);
                return ResponseEntity.ok(plan);
            } else return ResponseEntity.notFound().build();
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/dislikeC")
    public ResponseEntity<Plan> dislikePlanC(@RequestParam String planName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        try {
            User user = userService.findUserByEmail(email).orElseThrow();
            Optional<Plan> op = planService.findPlanByName(planName);
            if (op.isPresent()) {
                Plan plan = op.get();
                user.getLikedPlans().remove(plan);
                plan.setLikedUser(false);
                userService.update(user);
                return ResponseEntity.ok(plan);
            } else return ResponseEntity.notFound().build();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
    @PutMapping("/edited")
    public ResponseEntity<Plan> editPlan(@RequestParam String planName, @RequestParam String newDescription,
                                         @RequestParam String abv, @RequestParam int difficulty, HttpServletRequest request) {
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()) {
            Plan plan = op.get();
            if (!newDescription.isBlank()) {
                plan.setDescription(newDescription);
            }
            if (!abv.isBlank()) {
                plan.setAbv(abv);
            }
            if (difficulty != plan.getDifficulty()) {
                plan.setDifficulty(difficulty);
            }
            planService.save(plan);
            return ResponseEntity.ok(plan);
        } else return ResponseEntity.notFound().build();
    }
}
