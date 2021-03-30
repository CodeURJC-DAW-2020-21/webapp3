package es.dawequipo3.growing.controllerREST;


import com.fasterxml.jackson.annotation.JsonView;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/plan")
public class RESTPlan {

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    interface PlanDetails extends Plan.Categories, Plan.Basico, Category.Basico{

    }

    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/dislike")
    public ResponseEntity<Plan> dislikePlan(@RequestParam String planName, HttpServletRequest request){
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

    @JsonView(RESTPlan.PlanDetails.class)
    @PutMapping("/like")
    public ResponseEntity<Plan> likePlan(@RequestParam String planName, HttpServletRequest request){
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

}
