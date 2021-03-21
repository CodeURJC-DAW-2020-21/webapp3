package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;


@Controller
public class GrowingController {

    @Autowired
    private PlanService planService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;



    public void EmailFavoritesCategoryName(String email, String categoryName) {
        //Retrieve posible entities
        Optional<User> OptionalUser = userService.findUserByEmail(email);
        Optional<Category> OptionalCategory = categoryService.findByName(categoryName);

        if (OptionalUser.isPresent() && OptionalCategory.isPresent()) {
            //Get the necesary entities
            User user = OptionalUser.get();
            Category category = OptionalCategory.get();
            //Add to favorites
            user.FavoriteCategory(category);
            userService.save(user);
        }
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
        return "index";
    }


    @GetMapping("/explore")
    public String explore(Model model, HttpServletRequest request){
        model.addAttribute("registered", request.isUserInRole("USER"));
        List<Plan> page;
        if(request.isUserInRole("USER")) {
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            page = userService.GetPageOfTopPlans(user,0);
            for (Plan plan : page) {
                plan.setLikedUser(planService.existsLiked(plan.getName(), user.getEmail()));
            }
        }else{
            page = planService.GetPageable(0);
        }
        model.addAttribute("Plan",page);
        return "explore";
    }

    @GetMapping("/explore/{pageNumber}")
    public String ExploreRequestPlanPage(Model model, @PathVariable int pageNumber, HttpServletRequest request) {
        model.addAttribute("registered", request.isUserInRole("USER"));
        List<Plan> page;
        if(request.isUserInRole("USER")) {
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            page = userService.GetPageOfTopPlans(user,pageNumber);
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            for (Plan plan : page) {
                plan.setLikedUser(planService.existsLiked(plan.getName(), user.getEmail()));
            }
        }else{
            page = planService.GetPageable(pageNumber);
        }model.addAttribute("Plan", page);
        return "PlanTemplate";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model, HttpServletRequest request){
        model.addAttribute("registered", request.isUserInRole("USER"));
        return "AboutUs";
    }



    @GetMapping("/404-NotFound")
    public String notFound(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "error/404";
    }

    @GetMapping("/500-ServerError")
    public String serverError(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "error/500";
    }

}