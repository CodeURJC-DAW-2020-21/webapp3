package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(){
        return "editProfile";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name, HttpServletRequest request){
        model.addAttribute("category", categoryService.findByName(name).orElseThrow());
        model.addAttribute("date", "11-3-2020");
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        model.addAttribute("liked", true);
        return "categoryInfo";
    }

    @GetMapping("/newCategory")
    public String newCategory(Model model){
        model.addAttribute("category",new Category());
        return "addCategory";
    }

    @PostMapping("/newCategory")
    public String addCategory(@RequestParam String name, @RequestParam String description, @RequestParam String icon, @RequestParam String color){
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setColor(color);
        category.setIcon(icon);
        category.setDate(new Date());

        return "redirect:/explore";
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