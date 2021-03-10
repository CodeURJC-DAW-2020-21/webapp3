package es.dawequipo3.growing;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import java.util.List;


@Controller
public class GrowingController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private Completed_planRepository completed_planRepository;

    @Autowired
    private UserRepository userRepository;


   @PostConstruct
    public void init() {

        Category mentalHealth = new Category("Mental health", "Because we know that having a good mental health is fundamental, we want to share with you some\n" +
                "                        tasks for helping you to achieve it", "ri-mental-health-line icon", "blue");
        categoryRepository.save(mentalHealth);

        Category physicalHealth = new Category("Physical health", "Do you want to be fit making regular exercise but you are a lazy person? Look at the plans of\n" +
                "                        this category and reject the lazyness!", "ri-run-line icon", "red");
        categoryRepository.save(physicalHealth);

        Category savings = new Category("Savings", "Everyone want to have money but most of the people waste it in stuff they don't need. In this\n" +
                "                        category we will help you with some tips\n" +
                "                        to increase your savings and not wasting them.", "ri-money-euro-circle-line icon", "green");
        categoryRepository.save(savings);

        Category goodNight = new Category("Good night", "Do you have insomnia? Here you will see multiple tasks that you can do to sleep like a baby\n" +
                "                        during the night", "ri-hotel-bed-fill icon", "bluenavy");
        categoryRepository.save(goodNight);

        Category home = new Category("Home", "Do you have to do home tasks but you leave them to the end or you even make them? This is your\n" +
                "                        category! You won't forget them and finish them quickly", "ri-home-2-line icon", "orange");
        categoryRepository.save(home);

        categoryRepository.save(new Category("Focus and Effort", "Do you want to be challenged daily? I challenge you to follow the pace of this category and\n" +
       "                        complete the refreshing daily challenge", "ri-focus-2-line icon", "purple"));

        Plan plan1 = new Plan("Meditation", "This is for meditate", 3);
        Plan plan2 = new Plan("Football", "This is for football", 3);
        plan1.setCategory(mentalHealth);
        plan2.setCategory(mentalHealth);

        planRepository.save(plan1);
        planRepository.save(plan2);

       User u1 = new User("p1@gmail.com","paquito","contraseña");
       User u2 = new User("yo@hotmail.com","XXXkillerG0D99XXX","IAmVeryMature");
       Tree t1= new Tree(u1,goodNight,0, Calendar.getInstance().getTimeInMillis());
       Tree t2= new Tree(u2,physicalHealth,0, Calendar.getInstance().getTimeInMillis());
        
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

   @GetMapping("/categories")
    public String categories(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", categories);
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
        model.addAttribute("category",categoryRepository.findCategoryByName(name).orElseThrow());
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