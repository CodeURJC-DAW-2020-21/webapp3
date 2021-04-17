package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompletedPlanService completedPlanService;
    @Autowired
    private PlanService planService;

    @Autowired
    private CategoryService categoryService;

    /**
     * This method allows the user to sign up after completing some parameters
     *
     * @param username        the username
     * @param surname         the surname
     * @param email           the primary key, unique for each user
     * @param name            the name
     * @param password        the password
     * @param confirmPassword to check and prevent user mistakes
     * @param imageFile       profile image, can be empty. In this case, it will be provided with a default one
     * @return
     * @throws IOException
     */
    @PostMapping("/getStarted/signUp")
    public String signUp(@RequestParam String username, @RequestParam String surname, @RequestParam String email,
                         @RequestParam String name, @RequestParam String password, @RequestParam String confirmPassword, MultipartFile imageFile) throws IOException {


        User user = new User(email, username, name, surname, password, "USER");

        if (password.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(user.getEncodedPassword()));
        }

        boolean error = !password.equals(confirmPassword);

        if (!imageFile.isEmpty()) {
            user.setImageFile(BlobProxy.generateProxy(
                    imageFile.getInputStream(), imageFile.getSize()));
        }

        error = userService.findUserByEmail(user.getEmail()).isPresent() || error;

        if (error) {
            return "redirect:/getStarted";
        }

        userService.save(user);

        return "redirect:/getStarted";
    }

    /**
     * Redirect to the login and sign up page
     *
     * @param model
     * @param request
     * @return getStarted.html
     */

    @GetMapping("/getStarted")
    public String getStarted(Model model, HttpServletRequest request) {
        model.addAttribute("error", request.isRequestedSessionIdFromCookie());
        return "getStarted";
    }

    /**
     * This get the user to its profile. If is admin, the profile card will be different and a historic table will appear
     * In that table, it can filter the users and remove tasks done by users.
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        model.addAttribute("category", categoryService.findAll());
        User user = userService.findUserByEmail(email).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        List<Completed_plan> completed_planList = completedPlanService.getAllCompletedPlans(request);
        model.addAttribute("CompletedPlan", completed_planList);
        return "profile";
    }

    /**
     * This method receives the apply of the find button and charges the screen with the data
     *
     * @param emailSearched
     * @return
     */
    @PostMapping("/profile/searchEmail")
    public String profileAdminTableRequest(@RequestParam String emailSearched) {
        if (emailSearched.isBlank()) {
            return "redirect:/profile";
        } else return "redirect:/profile/" + emailSearched;
    }

    /**
     * This method charges the table with filtered results
     *
     * @param model
     * @param emailSearched will be the parameter to filter its plans on the admin's table
     * @param request
     * @return
     */
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

    /**
     * This method charges the edit screen with profile settings
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("isProfile", true);
        model.addAttribute("isCategory", false);
        model.addAttribute("isPlan", false);
        return "EditScreen";
    }

    /**
     * This method charges the edit screen with plan settings. Page only allowed to admin
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/editPlan/{planName}")
    public String goToeditPlan(Model model, @PathVariable String planName, HttpServletRequest request) {
        Plan plan = planService.findPlanByName(planName).orElseThrow();
        if (request.isUserInRole("ADMIN")) {
            model.addAttribute("plan", plan);
            model.addAttribute("isProfile", false);
            model.addAttribute("isCategory", false);
            model.addAttribute("isPlan", true);
            return "EditScreen";
        } else return "redirect:/";
    }


    /**
     * This method applies the change of the actual Plan parameters to the new ones filled on the forms only if the text
     * area is not blank
     *
     * @param planName
     * @param newDescription
     * @param abv
     * @param difficulty
     * @return
     */
    @PostMapping("/editCategory/{categoryName}/{planName}/completed")
    public String editPlan(@PathVariable String planName,
                           @RequestParam String newDescription, @RequestParam String abv, @RequestParam int difficulty) {

        planService.editPlan(planName,newDescription,abv,difficulty);
        return "redirect:/";

    }

    /**
     *
     * @param category
     * @param planName
     * @param abv
     * @param description
     * @param difficulty
     * @return
     */
    @PostMapping("/categoryInfo/{category}/addPlan")
    public String createPlan(@PathVariable String category, @RequestParam String planName, @RequestParam String abv, @RequestParam String description,
                             @RequestParam int difficulty) {

        Plan plan = planService.createPlan(planName,category,abv,description,difficulty);
        return "redirect:/categoryInfo/{category}";
    }

    /**
     * This method receives the data charged on the editProfile forms and saves the new user parameters
     *
     * @param username
     * @param name
     * @param surname
     * @param encodedPassword
     * @param confirmEncodedPassword
     * @param imageFile
     * @param request
     * @return
     * @throws IOException if there is an error with the image
     */

    @PostMapping("/editProfileAction")
    public String changeUserData(@RequestParam String username, @RequestParam String name,
                                 @RequestParam String surname, @RequestParam String encodedPassword,
                                 @RequestParam String confirmEncodedPassword, MultipartFile imageFile,
                                 HttpServletRequest request) throws IOException {

        String actualEmail = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(actualEmail).orElseThrow();
        if (!username.isBlank()) {
            user.setUsername(username);
        }
        if (!name.isBlank()) {
            user.setName(name);
        }
        if (!surname.isBlank()) {
            user.setSurname(surname);
        }
        if (!encodedPassword.isBlank() && (encodedPassword.equals(confirmEncodedPassword))) {
            user.setEncodedPassword(passwordEncoder.encode(encodedPassword));
        }
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                user.setImageFile(BlobProxy.generateProxy(
                        imageFile.getInputStream(), imageFile.getSize()));
            }
        }
        userService.update(user);
        return "redirect:/profile";
    }

}
