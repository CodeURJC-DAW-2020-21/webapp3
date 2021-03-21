package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.CompletedPlanService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompletedPlanService completedPlanService;

    @Autowired
    private PlanService planService;

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/getStarted/signUp")
    public String signUp(@RequestParam String username, @RequestParam String surname, @RequestParam String email,
                         @RequestParam String name,@RequestParam String password, @RequestParam String confirmPassword, MultipartFile imageFile) throws IOException {

        User user = new User(email, username, name, surname, password, "USER");
        if (password.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(user.getEncodedPassword()));
        }

        if (!imageFile.isEmpty()) {
                user.setImageFile(BlobProxy.generateProxy(
                        imageFile.getInputStream(), imageFile.getSize()));
        }

        userService.save(user);
        return "redirect:/getStarted";
    }

    @GetMapping("/getStarted")
    public String getStarted(Model model, HttpServletRequest request){
        model.addAttribute("error", request.isRequestedSessionIdFromCookie());
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        model.addAttribute("category", categoryService.findAll());
        User user = userService.findUserByEmail(email).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        List<Completed_plan> completed_planList = completedPlanService.getAllCompletedPlans(request);
        model.addAttribute("CompletedPlan", completed_planList);
        return "profile";
    }

    @PostMapping("/profile/searchEmail")
    public String profileAdminTableRequest(@RequestParam String emailSearched) {
        if (emailSearched.isBlank()){
            return "redirect:/profile";
        }
        else return "redirect:/profile/"+emailSearched;
    }

    @GetMapping("/profile/{emailSearched}")
    public String profileAdminTableRequestResult(Model model, @PathVariable String emailSearched, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        model.addAttribute("category", categoryService.findAll());
        User user = userService.findUserByEmail(email).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        List<Completed_plan> completed_planList = completedPlanService.getCompletedPlanPageByEmailSortedByDate(emailSearched);
        model.addAttribute("CompletedPlan", completed_planList);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("isProfile", true);
        model.addAttribute("isCategory", false);
        model.addAttribute("isPlan", false);
        return "EditScreen";
    }

    @PostMapping("/editPlan/{planName}")
    public String editPlan(Model model, @PathVariable String planName, HttpServletRequest request){
        Plan plan = planService.findPlanByName(planName).orElseThrow();
        model.addAttribute("plan", plan);
        model.addAttribute("isProfile", false);
        model.addAttribute("isCategory", false);
        model.addAttribute("isPlan", true);
        return "EditScreen";
    }


    @PostMapping("/editProfileAction")
    public String changeUserData(@RequestParam String username, @RequestParam String name,
                                 @RequestParam String surname, @RequestParam String encodedPassword,
                                 @RequestParam String confirmEncodedPassword, MultipartFile imageFile,
                                 HttpServletRequest request) throws IOException {

        String actualEmail = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(actualEmail).orElseThrow();
        if (!username.isBlank()){
            user.setUsername(username);
        }
        if (!name.isBlank()){
            user.setName(name);
        }
        if (!surname.isBlank()){
            user.setSurname(surname);
        }
        if (!encodedPassword.isBlank() && (encodedPassword.equals(confirmEncodedPassword))){
            user.setEncodedPassword(encodedPassword);
        }
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                user.setImageFile(BlobProxy.generateProxy(
                        imageFile.getInputStream(), imageFile.getSize()));
            }
        }
        userRepository.save(user);
        return "redirect:/profile";
    }

}