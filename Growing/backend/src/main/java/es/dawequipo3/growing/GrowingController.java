package es.dawequipo3.growing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class GrowingController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/categories")
    public String categories(){
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

    @GetMapping("/profileAdmin")
    public String profileAdmin(){
        return "profileAdmin";
    }

    @GetMapping("/categoryInfo")
    public String categoryInfo(Model model){

        model.addAttribute("title", "Mental Health");
        model.addAttribute("description", "In this category we show you a group of plans that you must carry out every day to\n" +
                "                    improve your mental health. The tree will increase its height when you complete some plans and it\n" +
                "                    will lower when you don't.");
        model.addAttribute("date", "11/03/2020");

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
