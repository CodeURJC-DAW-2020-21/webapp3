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

@Controller
public class PlanLikeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;


    /**
     * Added to the user_liked_plans the relationship between the current user and the plan which has its abbreviation
     *
     * @param abbrev  the abbreviation
     * @param request
     */
    @GetMapping("/categoryInfo/{abbrev}/like")
    public void likePlan(@PathVariable String abbrev, HttpServletRequest request) {
        planService.likePlanAbbr(abbrev,request);
    }

    /**
     *
     * @param abbrev
     * @param request
     */
    @GetMapping("/categoryInfo/{abbrev}/dislike")
    public void dislikePlan(@PathVariable String abbrev, HttpServletRequest request) {
        planService.dislikePlanAbbr(abbrev,request);
    }

    /**
     *
     * @param name
     * @param planName
     * @param request
     * @return
     */
    @PostMapping("/categoryInfo/{name}/{planName}/like")
    public String likePlanC(@PathVariable String name, @PathVariable String planName, HttpServletRequest request) {
        planService.likePlanName(planName,request);
        return "redirect:/categoryInfo/{name}";
    }

    /**
     *
     * @param name
     * @param planName
     * @param request
     * @return
     */
    @PostMapping("/categoryInfo/{name}/{planName}/dislike")
    public String dislikePlanC(@PathVariable String name, @PathVariable String planName, HttpServletRequest request) {
        planService.dislikePlanName(planName,request);
        return "redirect:/categoryInfo/{name}";
    }

}
