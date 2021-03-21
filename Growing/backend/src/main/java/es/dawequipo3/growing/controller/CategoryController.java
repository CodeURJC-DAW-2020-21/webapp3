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

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping("/complete/{name}")
    public String updateTree(@PathVariable String name, HttpServletRequest request) {
        Plan plan = planService.findPlanByName(name).orElseThrow();
        Category category = plan.getCategory();
        request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(request.getUserPrincipal().getName()).orElseThrow();
        Tree tree = treeService.findTree(user.getEmail(), category.getName()).orElseThrow();
        treeService.UpdateTreeNewPlan(tree, plan, user.getEmail());
        planService.saveCompletedPlan(user, plan);

        return "redirect:/categories";
    }

    @PostMapping("/removeCompletedPlan/{email}/{plan}/{date}")
    public String removeCompletedPlan(@PathVariable String email, @PathVariable String plan, @PathVariable String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        try {
            Date dateObject = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateObject);
            long milisecs = calendar.getTimeInMillis();
            completedPlanService.deleteCompletedPlan(email, plan, milisecs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/profile";
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest request){
        model.addAttribute("error",request.isRequestedSessionIdFromCookie());
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

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String name,@RequestParam String des,
                                 @RequestParam String icon, @RequestParam String color){

        boolean error;

        Category category = new Category(name,des,icon,color);
        error = categoryService.findByName(category.getName()).isPresent();
        if(!error){
            categoryRepository.save(category);
        }

        return "categories";

    }

    @PostMapping("/categoryInfo/{category}/addPlan")
    public String createPlan(@PathVariable String category,@RequestParam String planName, @RequestParam String description,
                            @RequestParam int difficulty){


        boolean error;
        logger.info(category);
        Plan newPlan = new Plan(planName,description,difficulty,category);
        Category categoryName = categoryService.findByName(category).orElseThrow();
        List<Plan> planList = categoryName.getPlans();
        error = categoryService.findByName(newPlan.getName()).isPresent();
        if(!error){
            planList.add(newPlan);
            categoryRepository.save(categoryName);
        }

        return "redirect:/categories";
    }

}