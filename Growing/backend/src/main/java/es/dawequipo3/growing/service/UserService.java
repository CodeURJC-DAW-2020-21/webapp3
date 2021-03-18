package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private EmailService emailService;


    public void save(User user){
        userRepository.save(user);
        for (Category category: categoryService.findAll()) {
            treeService.save(new Tree(user, category));
        }
        emailService.sendEmailRegister(user.getEmail());
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public Optional<User> findUserByEmail(String email){
        return userRepository.findById(email);
    }
    public Optional<User> findUserByName(String name){
        return userRepository.findByUsername(name);
    }
}
