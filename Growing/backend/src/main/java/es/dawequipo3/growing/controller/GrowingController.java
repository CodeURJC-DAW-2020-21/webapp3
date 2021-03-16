package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.*;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;


@Controller
public class GrowingController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;


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


    @PostMapping("/getStarted/signUp")
    public String signUp(User user) {
        if (user.getPassword().equals(user.getConfirmPassword()))
            userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/explore")
    public String explore(Model model) {
        model.addAttribute("registered", true);
        return "explore";
    }


    @GetMapping("/aboutUs")
    public String aboutUs(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "AboutUs";
    }

    @GetMapping("/getStarted")
    public String getStarted(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("error", request.isRequestedSessionIdValid());
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request){
        model.addAttribute("category", categoryService.findAll());
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        return "profile";
    }

    @GetMapping("/profile/{name}")
    public String profileTaskByCategory(Model model, @PathVariable String name, HttpServletRequest request){
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        Category category = categoryService.findByName(name).orElseThrow();
        for (Plan plan: planService.likedplans("p1@gmail.com", category.getName())){
            plan.setLikedUser(true);
        }
        model.addAttribute("category", category);
    }

    @GetMapping("/editProfile")
    public String editProfile(){
        return "editProfile";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name, HttpServletRequest request){
        Category category = categoryService.findByName(name).orElseThrow();
        for (Plan plan: category.getPlans()){
            plan.setLikedUser(planService.existsLiked(plan.getName(), "p1@gmail.com"));
        }
        model.addAttribute("category", category);
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        return "categoryInfo";
    }

    @GetMapping("/404-NotFound")
    public String notFound(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "/error/404";
    }

    @GetMapping("/500-ServerError")
    public String serverError(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "/error/500";
    }

}