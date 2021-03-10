package es.dawequipo3.growing;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.*;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import java.util.List;


@Controller
public class GrowingController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/categories")
    public String categories(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("category", categories);
        return "categories";
    }

    @PostMapping("/getStarted/signUp")
    public String signUp(User user){
        if (user.getPassword().equals(user.getConfirmPassword()))
            userService.save(user);

        return "/";
    }

    @PostMapping("/activityCompleted")
    public void updateTree(){

    }

    @GetMapping("/explore")
    public String explore(){
        return "explore";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "AboutUs";
    }

    @GetMapping("/getStarted")
    public String getStarted(){
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("admin", false);
        return "profile";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name){
        model.addAttribute("category",categoryService.findByName(name).orElseThrow());
        model.addAttribute("date", "11-3-2020");
        model.addAttribute("registered", true);
        model.addAttribute("admin", true);
        model.addAttribute("liked", true);
        return "categoryInfo";
    }

    @GetMapping("/404-NotFound")
    public String notFound(){
        return "404";
    }

    @GetMapping("/500-ServerError")
    public String serverError(){
        return "500";
    }
}