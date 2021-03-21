package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class GrowingController {

    @Autowired
    private PlanService planService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


    /**
     * Inex page, only differentiated by the user log status on the getStarted or Sign Out button
     *
     * @param model
     * @param request
     * @return
     */

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
        return "index";
    }

    /**
     * This method loads the first 10 random plans for the anonymous users and the recommended plans to registered ones
     * If the user is admin, buttons of edit plans and category will appear.
     *
     * @param model
     * @param request
     * @return
     */

    @GetMapping("/explore")
    public String explore(Model model, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        List<Plan> page;
        if (request.isUserInRole("USER")) {
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            page = userService.GetPageOfTopPlans(user, 0);
            for (Plan plan : page) {
                plan.setLikedUser(planService.existsLiked(plan.getName(), user.getEmail()));
            }
        } else {
            page = planService.GetPageable(0);
        }
        model.addAttribute("Plan", page);
        return "explore";
    }

    /**
     * This method is the pagination, which loads the correspondent {pageNumber} page
     *
     * @param model
     * @param request
     * @param pageNumber this is the actual page loaded, which contains the elements inside it
     * @return PlanTemplate.html
     */
    @GetMapping("/explore/{pageNumber}")
    public String ExploreRequestPlanPage(Model model, @PathVariable int pageNumber, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        List<Plan> page;
        if (request.isUserInRole("USER")) {
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            page = userService.GetPageOfTopPlans(user, pageNumber);
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            for (Plan plan : page) {
                plan.setLikedUser(planService.existsLiked(plan.getName(), user.getEmail()));
            }
        } else {
            page = planService.GetPageable(pageNumber);
        }
        model.addAttribute("Plan", page);
        return "PlanTemplate";
    }

    /**
     * This loads the about us page
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/aboutUs")
    public String aboutUs(Model model, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        return "AboutUs";
    }

    /**
     * This loads the resource not found page
     *
     * @param model
     * @param request
     * @return
     */

    @GetMapping("/404-NotFound")
    public String notFound(Model model, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        return "error/404";
    }

    /**
     * This loads the server error page
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/500-ServerError")
    public String serverError(Model model, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        return "error/500";
    }

}