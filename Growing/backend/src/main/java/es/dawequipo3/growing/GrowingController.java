package es.dawequipo3.growing;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Controller
public class GrowingController {

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

    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
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
    public String explore(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
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
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(){
        return "editProfile";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name, HttpServletRequest request){
        model.addAttribute("category", categoryService.findByName(name));
        model.addAttribute("description", categoryService.findByName(name).orElseThrow().getDescription());
        model.addAttribute("date", "11-3-2020");
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        model.addAttribute("liked", true);
        return "categoryInfo";
    }

    @GetMapping("/404-NotFound")
    public String notFound(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "404";
    }

    @GetMapping("/500-ServerError")
    public String serverError(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "500";
    }
}