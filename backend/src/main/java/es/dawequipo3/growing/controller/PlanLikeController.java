package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PlanLikeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Autowired
    private UserRepository userRepository;


    /**
     * Added to the user_liked_plans the relationship between the current user and the plan which has its abbreviation
     * @param abbrev the abbreviation
     * @param request
     */
    @GetMapping("/categoryInfo/{abbrev}/like")
    public void likePlan(@PathVariable String abbrev, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().add(planService.findPlanByAbbr(abbrev));
        userRepository.save(user);
    }

    @GetMapping("/categoryInfo/{abbrev}/dislike")
    public void dislikePlan( @PathVariable String abbrev, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().remove(planService.findPlanByAbbr(abbrev));
        userRepository.save(user);
    }

    @PostMapping("/categoryInfo/{name}/{planName}/like")
    public String likePlanC(@PathVariable String name, @PathVariable String planName, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().add(planService.findPlanByName(planName).orElseThrow());
        userRepository.save(user);
        return "redirect:/categoryInfo/"+ name;
    }

    @PostMapping("/categoryInfo/{name}/{planName}/dislike")
    public String dislikePlanC(@PathVariable String name, @PathVariable String planName, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().remove(planService.findPlanByName(planName).orElseThrow());
        userRepository.save(user);
        return "redirect:/categoryInfo/"+ name;
    }

}
