package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    private PlanService planService;

    @PostConstruct
    public void init() {
        User u1 = new User("p1@gmail.com", "pericopalotes909", "perico", "palotes", "contraseña");
        User u2 = new User("yo@hotmail.com", "XXXkillerG0D99XXX", "josé", "garcia", "IAmVeryMature");

        userRepository.save(u1);
        userRepository.save(u2);
    }

    public List<Plan> GetTopPlans(User user) {
        //First goes the liked plans
        List<Plan> firstResult = user.getLikedPlans();
        //Second goes the completed categories
        List<Plan> secondResult = planService.findPlanFromLikedCategories(user);
        //Third goes the rest of plans
        List<Plan> thirdResults= planService.findAll();

        Set<Plan> planSet = new LinkedHashSet<>(firstResult);
        planSet.addAll(secondResult);
        planSet.addAll(thirdResults);
        List<Plan> finalList = new ArrayList<>(planSet);
        return finalList;
    }

    public void save(User user) {
        userRepository.save(user);
        for (Category category : categoryService.findAll()) {
            treeService.save(new Tree(user, category));
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findById(email);
    }
}
