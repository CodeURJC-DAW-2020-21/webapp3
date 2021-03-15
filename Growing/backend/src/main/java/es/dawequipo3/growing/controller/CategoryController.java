package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlanService planService;

    @GetMapping("/categories")
    public String categories(Model model){
        model.addAttribute("category", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name){
        model.addAttribute("category",categoryService.findByName(name).orElseThrow());
        model.addAttribute("plans", planService.findPlansByCategory(name));
        model.addAttribute("level", "1");
        model.addAttribute("registered", true);
        model.addAttribute("admin", true);
        model.addAttribute("liked", true);
        return "categoryInfo";
    }


}