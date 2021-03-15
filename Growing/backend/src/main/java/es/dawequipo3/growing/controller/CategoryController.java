package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
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


    @PostMapping("/complete/{name}")
    public String updateTree(Model model, @PathVariable String name) {
        Plan plan = planService.findPlanByName(name).orElseThrow();
        Category category = plan.getCategory();
        Tree tree = treeService.findTree("p1@gmail.com", category.getName()).orElseThrow();
        treeService.updateHeight(tree, plan, "j.ad.p2370@gmail.com");

        planService.saveCompletedPlan(userService.findUserByEmail("p1@gmail.com").orElseThrow(), plan);
        categoryService.refreshDate(category);

        return "redirect:/categories";
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
        return "categories";
    }



}