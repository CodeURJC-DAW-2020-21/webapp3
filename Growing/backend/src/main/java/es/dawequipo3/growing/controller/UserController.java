package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import es.dawequipo3.growing.service.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/getStarted/signUp")
    public String signUp(Model model,HttpServletRequest request, @RequestParam String username, @RequestParam String surname, @RequestParam String email,
                         @RequestParam String name,@RequestParam String password, @RequestParam String confirmPassword, MultipartFile imageFile
    ) throws IOException {
        boolean error = false;
        User user = new User(email, username, name, surname, password, "USER");
        error=!password.equals(confirmPassword);
        if (error) {
            user.setPassword(passwordEncoder.encode(user.getEncodedPassword()));
        }

        if (!imageFile.isEmpty()) {
            user.setImageFile(BlobProxy.generateProxy(
                    imageFile.getInputStream(), imageFile.getSize()));
        }
        error=userService.findUserByEmail(user.getEmail()).isPresent();
        if (error) {
            return "getStarted";
        } else {
            userService.save(user);
            User userName = userRepository.findByUsername(user.getName()).orElseThrow();

            model.addAttribute("user",userName);
            model.addAttribute("admin",request.isUserInRole(("ADMIN")));
            return "redirect:/profile";
        }
    }

    @GetMapping("/getStarted")
    public String getStarted(Model model, HttpServletRequest request){
        model.addAttribute("registered",request.isUserInRole("USER"));
        model.addAttribute("error", request.isRequestedSessionIdFromCookie());
        return "getStarted";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        model.addAttribute("user", user);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/editProfileAction")
    public String changeUserData(@RequestParam String username, @RequestParam String name,
                                 @RequestParam String surname, @RequestParam String encodedPassword,
                                 @RequestParam String confirmEncodedPassword, MultipartFile imageFile,
                                 HttpServletRequest request) throws IOException {

        String actualUsername = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(actualUsername).orElseThrow();
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