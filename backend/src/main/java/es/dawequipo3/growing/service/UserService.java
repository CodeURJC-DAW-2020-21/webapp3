package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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


    public List<Plan> GetPageOfTopPlans(User user, int pageNum) {


        List<Plan> plans = GetTopPlans(user);

        // Creation
        PagedListHolder<Plan> page = new PagedListHolder<>(plans);
        page.setPageSize(10);
        // Retrieval
        page.setPage(pageNum);
        if (pageNum>=page.getPageCount()){
            return Collections.emptyList();
        }else{
            return page.getPageList();
        }

    }

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

}
