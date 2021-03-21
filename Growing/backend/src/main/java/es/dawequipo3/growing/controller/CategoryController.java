package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.CategoryRepository;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.*;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            model.addAttribute("date", treeService.findTree(email, category.getName()).orElseThrow().getDate());
            model.addAttribute("image", treeService.findTree(email, category.getName()).orElseThrow().getImagePath());
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

    @PostMapping("/editCategory/{categoryName}")
    public String goToeditCategory(Model model, @PathVariable String categoryName, HttpServletRequest request){
        Category category = categoryService.findByName(categoryName).orElseThrow();
        model.addAttribute("category", category);
        model.addAttribute("isProfile", false);
        model.addAttribute("isCategory", true);
        model.addAttribute("isPlan", false);
        return "EditScreen";
    }

    @PostMapping("/editCategory/{categoryName}/completed")
    public String editCategory(Model model, @PathVariable String categoryName,
                                   @RequestParam String newDescription, @RequestParam String color, MultipartFile imageFile) throws IOException {

        Category category = categoryService.findByName(categoryName).orElseThrow();

        if (!newDescription.isBlank()){
            category.setDescription(newDescription);
        }
        category.setColor(color);

        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                category.setIcon(BlobProxy.generateProxy(
                        imageFile.getInputStream(), imageFile.getSize()));
            }
        }
        categoryRepository.save(category);
        return "redirect:/profile";

    }

}