package es.dawequipo3.growing;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataBaseLoader {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @PostConstruct
    public void dataInitialization() {


        // USER
        userService.save(new User("user@gmail","user","Evarist","Oh", passwordEncoder.encode("pass"), "USER"));
        // ADMIN
        userService.save(new User("admin@gmail","admin","Naomi","Watts", passwordEncoder.encode("pass"), "ADMIN","USER"));

        // CATEGORIES
        categoryService.save(new Category("Mental health", "Because we know that having a good mental health is fundamental, we want to share with you some\n" +
                "tasks for helping you to achieve it", "ri-mental-health-line icon", "blue"));

        categoryService.save(new Category("Physical health", "Do you want to be fit making regular exercise but you are a lazy person? Look at the plans of\n" +
                "this category and reject the lazyness!", "ri-run-line icon", "red"));

        categoryService.save(new Category("Savings", "Everyone want to have money but most of the people waste it in stuff they don't need. In this\n" +
                "category we will help you with some tips\n" +
                "to increase your savings and not wasting them.", "ri-money-euro-circle-line icon", "green"));

        categoryService.save(new Category("Good night", "Do you have insomnia? Here you will see multiple tasks that you can do to sleep like a baby\n" +
                "during the night", "ri-hotel-bed-fill icon", "bluenavy"));

        categoryService.save(new Category("Home", "Do you have to do home tasks but you leave them to the end or you even make them? This is your\n" +
                "category! You won't forget them and finish them quickly", "ri-home-2-line icon", "orange"));

        categoryService.save(new Category("Focus-Effort", "Do you want to be challenged daily? I challenge you to follow the pace of this category and\n" +
                "complete the refreshing daily challenge", "ri-focus-2-line icon", "purple"));

        // PLANS

        Plan plan1 = new Plan("Meditation", "This is for meditate", 3);
        Plan plan2 = new Plan("Football", "This is for football", 1);

        plan1.setCategory(categoryService.findByName("Mental health").orElseThrow());
        plan2.setCategory(categoryService.findByName("Mental health").orElseThrow());

        planService.save(plan1);
        planService.save(plan2);



        plan1 = new Plan("Meditation", "This is for meditate", 3);
        plan2 = new Plan("Football", "This is for football", 1);

        plan1.setCategory(categoryService.findByName("Focus-Effort").orElseThrow());
        plan2.setCategory(categoryService.findByName("Focus-Effort").orElseThrow());

        planService.save(plan1);
        planService.save(plan2);

        plan1 = new Plan("oSjp8rthtyhdrtsg0p0c5", "This is for meditate", 3,categoryService.findByName("Savings").orElseThrow());
        plan2 = new Plan("I57eaMgsfdrtgfrlMHq", "This is for football", 1,categoryService.findByName("Savings").orElseThrow());


        planService.save(plan1);
        planService.save(plan2);


        plan1 = new Plan("oSjp80erhgrfagrep0c5", "This is for meditate", 3,categoryService.findByName("Home").orElseThrow());
        plan2 = new Plan("I57eaMlerahrfhbrtMHq", "This is for football", 2,categoryService.findByName("Home").orElseThrow());


        planService.save(plan1);
        planService.save(plan2);


        plan1 = new Plan("oSjp80p0c5", "This is for meditate", 3,categoryService.findByName("Physical Health").orElseThrow());
        plan2 = new Plan("I57eaMlMHq", "This is for football", 1,categoryService.findByName("Physical Health").orElseThrow());


        planService.save(plan1);
        planService.save(plan2);

        plan1 = new Plan("oSjp80p0c5", "This is for meditate", 3,categoryService.findByName("Good night").orElseThrow());
        plan2 = new Plan("I57eaMlMHq", "This is for football", 1,categoryService.findByName("Good night").orElseThrow());


        planService.save(plan1);
        planService.save(plan2);

    }

}
