package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private PlanService planService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param user
     * @param pageNum
     * @return
     */
    public List<Plan> GetPageOfTopPlans(User user, int pageNum) {


        List<Plan> plans = GetTopPlans(user);

        // Creation
        PagedListHolder<Plan> page = new PagedListHolder<>(plans);
        page.setPageSize(10);
        // Retrieval
        page.setPage(pageNum);
        if (pageNum >= page.getPageCount()) {
            return Collections.emptyList();
        } else {
            return page.getPageList();
        }

    }

    /**
     * @param user
     * @return
     */
    public List<Plan> GetTopPlans(User user) {
        //First goes the liked plans
        List<Plan> firstResult = user.getLikedPlans();
        //Second goes the completed categories
        List<Plan> secondResult = planService.findPlanFromLikedCategories(user);
        //Third goes the rest of plans
        List<Plan> thirdResults = planService.findAll();

        Set<Plan> planSet = new LinkedHashSet<>(firstResult);
        planSet.addAll(secondResult);
        planSet.addAll(thirdResults);
        return new ArrayList<>(planSet);
    }

    /**
     * @param user
     */
    public void save(User user) {
        userRepository.save(user);
        for (Category category : categoryService.findAll()) {
            treeService.save(new Tree(user, category));
        }
        emailService.sendEmailRegister(user.getEmail());
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public ResponseEntity<User> returnUser(String email) {
        Optional<User> op = findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public User createUser(String email, String username, String name, String surname, String encodedPassword, String confirmEncodedPassword) {
        Optional<User> op = findUserByEmail(email);
        Optional<User> op1 = findUserByName(username);
        if (op.isEmpty() && op1.isEmpty() && encodedPassword.equals(confirmEncodedPassword)) {
            User user = new User(email, username, name, surname, passwordEncoder.encode(encodedPassword), "USER");
            save(user);
            return user;
        } else {
            return null;
        }
    }

    public User editUser(String email, String username, String name, String surname, String encodedPassword, String confirmEncodedPassword) {
        User user = findUserByEmail(email).orElseThrow();
        if (!username.isBlank() && findUserByName(username).isEmpty()) {
            user.setUsername(username);
        }
        if (!name.isBlank()) {
            user.setName(name);
        }
        if (!surname.isBlank()) {
            user.setSurname(surname);
        }
        if (!encodedPassword.isBlank() && !confirmEncodedPassword.isBlank() && encodedPassword.equals(confirmEncodedPassword)) {
            user.setEncodedPassword(passwordEncoder.encode(encodedPassword));
        }
        update(user);
        return user;
    }
}
