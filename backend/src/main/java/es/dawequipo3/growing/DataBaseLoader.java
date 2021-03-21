package es.dawequipo3.growing;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
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
        userService.save(new User("user@gmail", "user", "Evarist", "Oh", passwordEncoder.encode("pass"), "USER"));
        // ADMIN
        userService.save(new User("admin@gmail", "admin", "Naomi", "Watts", passwordEncoder.encode("pass"), "ADMIN", "USER"));

        // CATEGORIES
        categoryService.save(new Category("Mental health", "Because we know that having a good mental health is fundamental, we want to share with you some\n" +
                "tasks for helping you to achieve it", "mentalHealth", "blue"));

        categoryService.save(new Category("Physical health", "Do you want to be fit making regular exercise but you are a lazy person? Look at the plans of\n" +
                "this category and reject the lazyness!", "physical", "red"));

        categoryService.save(new Category("Savings", "Everyone want to have money but most of the people waste it in stuff they don't need. In this\n" +
                "category we will help you with some tips\n" +
                "to increase your savings and not wasting them.", "savings", "green"));

        categoryService.save(new Category("Good night", "Do you have insomnia? Here you will see multiple tasks that you can do to sleep like a baby\n" +
                "during the night", "goodnight", "darkblue"));

        categoryService.save(new Category("Home", "Do you have to do home tasks but you leave them to the end or you even make them? This is your\n" +
                "category! You won't forget them and finish them quickly", "home", "orange"));

        categoryService.save(new Category("Focus and Effort", "Do you want to be challenged daily? I challenge you to follow the pace of this category and\n" +
                "complete the refreshing daily challenge", "focus", "purple"));

        // PLANS

        // Mental Health
        planService.save(new Plan("Yoga", "You don't like yoga? Come on! This is because you didn't" +
                " start doing it. Play a class in YouTube and get into this healthy world!", 1,
                categoryService.findByName("Mental health").orElseThrow(), "yoga"));

        planService.save(new Plan("Puzzle", "We are sure you have a small puzzle. " +
                "Remove the dust of the box and chain 20 pieces. Let's go!", 1, categoryService.findByName("Mental health").orElseThrow(), "puzzle"));

        planService.save(new Plan("Puzzle level 2", "We know you can be good at this. Get a bigger puzzle and complete it. " +
                "Trust us, it is going to be good for your health", 2, categoryService.findByName("Mental health").orElseThrow(), "pzzlL2"));

        planService.save(new Plan("Rubik", "Next level, the puzzle is easy for you, Buy a rubik cube in your nearest shop and" +
                "is time to make magic", 3, categoryService.findByName("Mental health").orElseThrow(), "rubik"));


        // Physical Health
        planService.save(new Plan("Walk!", "This is the easiest one. Stop staring in front of the computer" +
                " screen and get out even to buy the bread. But walk a little more, 500 feet minimum, don't be lazy",
                1, categoryService.findByName("Physical health").orElseThrow(), "walk"));

        planService.save(new Plan("Cycling", "Who doesn't have a bicycle helmet and bike? Call your first" +
                " friend on the agenda and go for a ride! Take care, road is dangerous. We hope you can mark this task as done", 2, categoryService.findByName("Physical health").orElseThrow(), "cycl"));

        planService.save(new Plan("Running", "At this level you are an athlete, so the challenge is run " +
                "10 kilometers, mark this task as done for each 10 you achieve", 2, categoryService.findByName("Physical health").orElseThrow(), "run"));

        // Savings
        planService.save(new Plan("Candy savings", "Candys are beautiful, but they are not healthy. Remember" +
                " this sentence: \"a moment on the lips, forever on the hips\"" +
                " so the challenge is save 10 euros in a week that were spent on chocolate or sugar candys", 2, categoryService.findByName("Savings").orElseThrow(), "cndyS"));


        planService.save(new Plan("Swear jar", "Is awful to people hear rudeness, so the challenge is for" +
                " each mean word, you have to put into a jar 1 euro.\nIf you don't collect more than 7 euros in a week, " +
                "don't bother to mark this as done, don't lie to yourself", 2, categoryService.findByName("Savings").orElseThrow(), "swrJ"));

        // Home
        planService.save(new Plan("Do your bed!", "We know cleaning the room is boring and there is always one person who tells you:" +
                "clean the room, looks like a pigsty, so start doing your bed, you can listen to music meanwhile", 1, categoryService.findByName("Home").orElseThrow(), "dbed"));

        planService.save(new Plan("Bed is not the only thing to clean up", "You broke the ice and start " +
                "cleaning, so keep up the pace and clean your desk, because it is your workstation, you will be thankful with us", 2, categoryService.findByName("Home").orElseThrow(), "moreThanBed"));

        planService.save(new Plan("Cook something", "Every chef starts from the bottom, preparing eggs" +
                " or pasta. Approach the high amount of time at home to cook something", 3, categoryService.findByName("Home").orElseThrow(), "cook"));


        // Good night
        planService.save(new Plan("Watch Netflix", "After a stressful day in the office or studies,it is" +
                " not good to sleep inmediately!, you will be thinking of it and you won't sleep. So this task is watch " +
                "one or two Netflix or streaming content. You can watch it with your partner, double joy!", 2, categoryService.findByName("Good night").orElseThrow(), "netflix"));

        planService.save(new Plan("Read a book", "Search a book of your most liked topic and read at least" +
                " two chapters or 40 pages, we are sure you will disconnect", 2, categoryService.findByName("Good night").orElseThrow(), "read"));

        planService.save(new Plan("Paint mandalas", "This one is very easy, buy a mandalas book and paint" +
                " one", 1, categoryService.findByName("Good night").orElseThrow(), "mandala"));


        // Focus and effort
        planService.save(new Plan("Push ups", "Come on buddy! This is the easiest one, but you are advised," +
                " this is the most difficult category. This challenge is to achieve 3 series of 10 push ups, with 30 second breaks!", 3, categoryService.findByName("Focus and Effort").orElseThrow(), "pushUps"));

        planService.save(new Plan("Lunges", "You have to complete 5 series of 20 lunges, with 30 second breaks!" +
                " Don't mark this activity two consecutive days! we won't trust you, you need to rest one day minimum", 3, categoryService.findByName("Focus and Effort").orElseThrow(), "lunges"));


        planService.save(new Plan("Abs", "You have to complete 4 series of 20 abs, with 30 second breaks!" +
                " This is the most difficult one, at the moment!", 3, categoryService.findByName("Focus and Effort").orElseThrow(), "abs"));

    }

}