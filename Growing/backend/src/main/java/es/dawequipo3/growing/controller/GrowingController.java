package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.TreeService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class GrowingController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private PlanService planService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("category", categoryService.findAll());
        return "index";
    }

    @PostMapping("/complete/{name}")
    public String updateTree(Model model, @PathVariable String name) {
        Plan plan = planService.findPlanByName(name).orElseThrow();
        Category category = plan.getCategory();
        Tree tree = treeService.findTree("p1@gmail.com", category.getName()).orElseThrow();
        treeService.updateHeight(tree, plan, "insert email here");

        planService.saveCompletedPlan(userService.findUserByEmail("p1@gmail.com").orElseThrow(), plan);
        categoryService.refreshDate(category);

        return "redirect:/categories";
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
    public String aboutUs() {
        return "AboutUs";
    }

    @GetMapping("/getStarted")
    public String getStarted() {
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        model.addAttribute("admin", false);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile() {
        return "editProfile";
    }

}

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name){
        model.addAttribute("category",categoryService.findByName(name).orElseThrow());
        model.addAttribute("plans", planService.findPlansByCategory(name));
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