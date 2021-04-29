package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private CompletedPlanService completedPlanService;

    @Autowired
    private Completed_planRepository completed_planRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    public void save(Plan plan) {
        planRepository.save(plan);
    }

    public List<Plan> findPlansByCategory(String name) {
        return planRepository.findPlansByCategory_Name(name);
    }

    public Optional<Plan> findPlanByName(String name) {
        return planRepository.findPlansByName(name);
    }

    public Plan findPlanByAbbr(String abbr) {
        return planRepository.findPlansByAbv(abbr).orElseThrow();
    }

    public void saveCompletedPlan(User user, Plan plan) {
        Tree tree = treeService.findTree(user.getEmail(), plan.getCategory().getName()).orElseThrow();
        treeService.UpdateTreeNewPlan(tree, plan, user.getEmail());
        completed_planRepository.save(new Completed_plan(user, plan));
    }

    public void saveCompletedPlan(User user, Plan plan, long date) {
        Tree tree = treeService.findTree(user.getEmail(), plan.getCategory().getName()).orElseThrow();
        treeService.UpdateTreeNewPlan(tree, plan, user.getEmail());
        completed_planRepository.save(new Completed_plan(user, plan, date));
    }

    public List<Plan> GetPageable(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return planRepository.findAll(pageable).getContent();
    }

    public List<Plan> likedplans(String email, String cat) {
        return planRepository.getPlanByCategoryAndAndLikedUser(cat, email);
    }

    public Boolean existsLiked(String plan, String user) {
        return planRepository.existsLiked(plan, user) == 1;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public List<Plan> findPlanFromLikedCategories(User user) {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : user.getUserFavoritesCategory()) {
            categoryNames.add(category.getName());
        }
        return this.planRepository.getLikedPlanFromCategory(categoryNames);
    }

    public Page<Plan> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return planRepository.findAll(pageable);
    }

    public Plan createPlan(String planName, String categoryName, String abv, String description, int difficulty) {
        Optional<Plan> op = findPlanByName(planName);
        if (op.isEmpty() || (difficulty > 3 || difficulty < 1)) {
            Category category = categoryService.findByName(categoryName).orElseThrow();
            Plan plan = new Plan(planName, description,
                    difficulty, category, abv);
            save(plan);
            return plan;
        } else {
            return null;
        }
    }

    public Completed_plan removeCompletedPlan(String email, String planName, String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
        try {
            Date dateObject = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateObject);
            long milisecs = calendar.getTimeInMillis();
            Plan plan = findPlanByName(planName).orElseThrow();
            Completed_plan completed_plan = completedPlanService.findCompletedPlan(email, plan, milisecs).orElseThrow();
            completedPlanService.deleteCompletedPlan(email, planName, milisecs);
            return completed_plan;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Plan likePlanAbbr(String abbrev, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        if (!user.getLikedPlans().contains(planService.findPlanByAbbr(abbrev))) {
            user.getLikedPlans().add(planService.findPlanByAbbr(abbrev));
        }
        Plan plan= planService.findPlanByAbbr(abbrev);
        plan.setLikedUser(true);
        userService.update(user);
        return plan;
    }

    public Plan dislikePlanAbbr(String abbrev, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        user.getLikedPlans().remove(planService.findPlanByAbbr(abbrev));
        Plan plan= planService.findPlanByAbbr(abbrev);
        plan.setLikedUser(false);
        userService.update(user);
        return plan;
    }
    public Plan likePlanName(String planname, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Plan plan= planService.findPlanByName(planname).orElseThrow();
        if (!user.getLikedPlans().contains(plan)) {
            user.getLikedPlans().add(plan);
        }
        plan.setLikedUser(true);
        userService.update(user);
        return plan;
    }
    public Plan dislikePlanName(String planname, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Plan plan= planService.findPlanByName(planname).orElseThrow();
        user.getLikedPlans().remove(plan);
        plan.setLikedUser(false);
        userService.update(user);
        return plan;
    }

    public Plan editPlan(String planName,String newDescription,String abv,int difficulty) {
        Plan plan = planService.findPlanByName(planName).orElseThrow();

        if (!newDescription.isBlank()) {
            plan.setDescription(newDescription);
        }

        plan.setDifficulty(difficulty);

        if (!abv.isBlank()) {
            plan.setAbv(abv);
        }

        planService.save(plan);
        return plan;
    }
}
