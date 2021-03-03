package es.dawequipo3.growing;

import es.dawequipo3.growing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class GrowingController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("category", categoryService.findAll());
        return "index";
    }

    @GetMapping("/categories")
    public String categories(Model model){
        model.addAttribute("category", categoryService.findAll());
        return "categories";
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
        model.addAttribute("category", categoryService.findByName(name));
        model.addAttribute("description", categoryService.findByName(name).getDescription());
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

}