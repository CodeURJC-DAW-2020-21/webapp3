package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
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

    @PostMapping("/categoryInfo/{name}/{planName}/like")
    public String likePlan(@PathVariable String name, @PathVariable String planName, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        user.getLikedPlans().add(planService.findPlanByName(planName).orElseThrow());
        userRepository.save(user);
        return "redirect:/categoryInfo/"+ name;
    }

    @PostMapping("/categoryInfo/{name}/{planName}/dislike")
    public String dislikePlan(@PathVariable String name, @PathVariable String planName, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        user.getLikedPlans().remove(planService.findPlanByName(planName).orElseThrow());
        userRepository.save(user);
        return "redirect:/categoryInfo/"+ name;
    }

}
