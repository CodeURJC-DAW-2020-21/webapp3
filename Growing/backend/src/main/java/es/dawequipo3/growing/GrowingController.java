package es.dawequipo3.growing;

import es.dawequipo3.growing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


@Controller
public class GrowingController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
        return "index";
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("category", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/explore")
    public String explore(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
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
        model.addAttribute("error", true);
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "profile";
    }

    @GetMapping("/adminProfile")
    public String profileAdmin(Model model, HttpServletRequest request){
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        return "profileAdmin";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        return "editProfile";
    }

    @GetMapping("/categoryInfo/{name}")
    public String categoryInfo(Model model, @PathVariable String name, HttpServletRequest request){
        model.addAttribute("category", categoryService.findByName(name));
        model.addAttribute("description", categoryService.findByName(name).getDescription());
        model.addAttribute("date", "11-3-2020");
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("admin",request.isUserInRole("ADMIN"));
        model.addAttribute("liked", true);
        return "categoryInfo";
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