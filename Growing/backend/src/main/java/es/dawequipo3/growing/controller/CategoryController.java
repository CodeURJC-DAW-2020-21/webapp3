package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlanService planService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/complete/{name}")
    public String updateTree(Model model, @PathVariable String name, HttpServletRequest request) {
        Plan plan = planService.findPlanByName(name).orElseThrow();
        Category category = plan.getCategory();
        request.getUserPrincipal().getName();
        User user = userService.findUserByName(request.getUserPrincipal().getName()).orElseThrow();
        Tree tree = treeService.findTree(user.getEmail(), category.getName()).orElseThrow();
        treeService.updateHeight(tree, plan, user.getEmail());
        planService.saveCompletedPlan(user, plan);
        categoryService.refreshDate(category);

        return "redirect:/categories";
    }


    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest request){
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        Category category = categoryService.findByName(name).orElseThrow();
        if(request.isUserInRole("USER")) {
            String username = request.getUserPrincipal().getName();
            User user = userService.findUserByName(username).orElseThrow();
            for (Plan plan : category.getPlans()) {
                plan.setLikedUser(planService.existsLiked(plan.getName(), user.getEmail()));
            }
            category.setLikedByUser(user.getUserFavoritesCategory().contains(category));
        }
        model.addAttribute("category", category);

        return "categoryInfo";
    }

    @PostMapping("/categoryInfo/{name}/like")
    public String categoryLike(@PathVariable String name, HttpServletRequest request){
        Category category = categoryService.findByName(name).orElseThrow();
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        user.getUserFavoritesCategory().add(category);
        userRepository.save(user);
        return "redirect:/categoryInfo/{name}";
    }

    @PostMapping("/categoryInfo/{name}/dislike")
    public String categoryDislike(@PathVariable String name, HttpServletRequest request){
        Category category = categoryService.findByName(name).orElseThrow();
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        user.getUserFavoritesCategory().remove(category);
        userRepository.save(user);
        return "redirect:/categoryInfo/{name}";
    }

    @PostMapping("/addPlan")
    public String addPlan(Model model, @RequestParam String name, @RequestParam String descrip ,@RequestParam int difficulty){
        boolean error = false;
        Plan plan = new Plan(name,descrip,difficulty);
        error=planService.findPlanByName(name).isPresent();
        if(!error){
            planService.save(plan);
            return "explore";
        }
        else{
            return "categories";
        }
    }

    @PostMapping("/addCategory")
    public String addCategory(Model model, @RequestParam String name, @RequestParam String description,@RequestParam String icon,
                              @RequestParam String color){
        boolean error = false;
        Category category = new Category(name, description, icon, color, null);
        error= categoryService.findByName(name).isPresent();
        if(!error){
            categoryService.save(category);
        }
        return "redirect:/categories";
    }

}