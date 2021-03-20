package es.dawequipo3.growing;

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
    public String index() {
        User user = userService.findAll().get(0);
        userService.GetTopPlans(user);
        return "index";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("category", categories);
        return "categories";
    }

    @PostMapping("/getStarted/signUp")
    public String signUp(User user) {
        if (user.getPassword().equals(user.getConfirmPassword()))
            userService.save(user);
        return "/";
    }

    @PostMapping("/activityCompleted")
    public void updateTree() {

    }

    @GetMapping("/explore")
    public String explore() {
        return "explore";
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "AboutUs";
    }

    @GetMapping("/getStarted")
    public String getStarted() {
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("admin", false);
        return "profile";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name) {
        model.addAttribute("category", categoryService.findByName(name).orElseThrow());
        model.addAttribute("date", "11-3-2020");
        model.addAttribute("registered", true);
        model.addAttribute("admin", true);
        model.addAttribute("liked", true);
        return "categoryInfo";
    }

    @GetMapping("/404-NotFound")
    public String notFound() {
        return "404";
    }

    @GetMapping("/500-ServerError")
    public String serverError() {
        return "500";
    }
}