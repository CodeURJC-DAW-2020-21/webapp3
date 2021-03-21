package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.CategoryRepository;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PlanService planService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletedPlanService completedPlanService;


    @PostMapping("/complete/{name}")
    public String updateTree(Model model, @PathVariable String name, HttpServletRequest request) {
        Plan plan = planService.findPlanByName(name).orElseThrow();
        Category category = plan.getCategory();
        request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(request.getUserPrincipal().getName()).orElseThrow();
        Tree tree = treeService.findTree(user.getEmail(), category.getName()).orElseThrow();
        treeService.UpdateTreeNewPlan(tree, plan, user.getEmail());
        planService.saveCompletedPlan(user, plan);

        return "redirect:/categories";
    }

    @GetMapping("/RemoveCompletedPlan/")
    public String RemoveCompletedPlan(Model model, @RequestParam String email, @RequestParam String name, @RequestParam String date) {
        name=name.replace("+"," ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        try {
            Date dateObject = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateObject);
            long milisecs = calendar.getTimeInMillis();
            completedPlanService.DeleteCompletedPlan(email, name, milisecs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Completed_plan> completed_planList = completedPlanService.getCompletedPlanPageByEmailSortedByDate(email);
        model.addAttribute("admin", true);
        model.addAttribute("CompletedPlan", completed_planList);
        return "profile";
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        model.addAttribute("category", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        Category category = categoryService.findByName(name).orElseThrow();
        if(request.isUserInRole("USER")) {
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            model.addAttribute("date", treeService.findTree(email, category.getName()).orElseThrow().getDate());
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
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getUserFavoritesCategory().add(category);
        userRepository.save(user);
        return "redirect:/categoryInfo/{name}";
    }

    @PostMapping("/categoryInfo/{name}/dislike")
    public String categoryDislike(@PathVariable String name, HttpServletRequest request){
        Category category = categoryService.findByName(name).orElseThrow();
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getUserFavoritesCategory().remove(category);
        userRepository.save(user);
        return "redirect:/categoryInfo/{name}";
    }

    @PostMapping("/categories/addCategory")
    public String addCategory(@RequestParam String name,@RequestParam String des,
                                 @RequestParam String icon, @RequestParam String color){

        Logger logger = LoggerFactory.getLogger(UserController.class);

        boolean error = false;

        logger.info("Entramos en el metododo");
        Category category = new Category(name,des,icon,color);
        error = categoryService.findByName(category.getName()).isPresent();
        logger.info(category.getName());
        if(!error){
            categoryRepository.save(category);
        }

        return "redirect:/categories";

    }

    @PostMapping("/categories/{name}/addPlan")
    public String createPlan(@RequestParam String planName, @RequestParam String description,
                            @RequestParam int difficulty,@PathVariable String name){


        boolean error = false;
        Plan newPlan = new Plan(planName,description,difficulty,name);
        Category category = categoryService.findByName(name).orElseThrow();
        List<Plan> planList = category.getPlans();
        error = categoryService.findByName(newPlan.getName()).isPresent();
        if(!error){
            planList.add(newPlan);
            categoryRepository.save(category);
        }

        return "redirect:/categories";
    }

}