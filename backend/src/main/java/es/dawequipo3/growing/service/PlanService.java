package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private Completed_planRepository completed_planRepository;


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
        }else{
            return null;
        }
    }
}