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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

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

    interface PlanDetails extends Plan.Categories, Plan.Basico, Category.Basico{}
    interface CompletedPlanDetails extends Completed_plan.Basico, Plan.Basico, User.Basico{}

    @JsonView(PlanDetails.class)
    @GetMapping("/")
    public Collection<Plan> getPlans(){
        return planService.findAll();
    }

    @JsonView(PlanDetails.class)
    @GetMapping("")
    public ResponseEntity<Plan> getSpecificPlan(@RequestParam String planName) {
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()) {
            Plan plan = op.get();
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
                return new ResponseEntity<>(plan, HttpStatus.CREATED);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @JsonView(RESTPlan.PlanDetails.class)
    @PostMapping("/done")
    public ResponseEntity<Plan> completePlan(@RequestParam String planName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()){
            Plan plan = op.get();
            planService.saveCompletedPlan(user, plan);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @JsonView(CompletedPlanDetails.class)
    @GetMapping("/completedPlans")
    public Collection<Completed_plan> getAllCompletedPlan() {
        return completedPlanService.findall();
    }

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
            if (optionalPlan.isPresent()){
                Plan plan = optionalPlan.get();
                Optional<Completed_plan> optionalCompleted_plan = completedPlanService.findCompletedPlan(email, plan, milisecs);
                if (optionalCompleted_plan.isPresent()){
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

    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/like")
    public void likePlan(@RequestParam String abbrev, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().add(planService.findPlanByAbbr(abbrev));
        planService.findPlanByAbbr(abbrev).setLikedUser(true);
        userService.update(user);
    }

    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/dislike")
    public void dislikePlan(@RequestParam String abbrev, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().remove(planService.findPlanByAbbr(abbrev));
        planService.findPlanByAbbr(abbrev).setLikedUser(false);
        userService.update(user);
    }

    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/likeC")
    public ResponseEntity<Plan> likePlanC(@RequestParam String planName, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()){
            Plan plan = op.get();
            user.getLikedPlans().add(plan);
            plan.setLikedUser(true);
            userService.update(user);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/dislikeC")
    public ResponseEntity<Plan> dislikePlanC(@RequestParam String planName, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()){
            Plan plan = op.get();
            user.getLikedPlans().remove(plan);
            plan.setLikedUser(false);
            userService.update(user);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(PlanDetails.class)
    @PutMapping("/edited")
    public ResponseEntity<Plan> editPlan(@RequestParam String planName, @RequestParam String newDescription,
                                         @RequestParam String abv, @RequestParam int difficulty){

        Optional<Plan> op = planService.findPlanByName(planName);
        if (op.isPresent()){
            Plan plan = op.get();
            if (!newDescription.isBlank()){
                plan.setDescription(newDescription);
            }
            if (!abv.isBlank()){
                plan.setAbv(abv);
            }
            if (difficulty != plan.getDifficulty()){
                plan.setDifficulty(difficulty);
            }
            planService.save(plan);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
