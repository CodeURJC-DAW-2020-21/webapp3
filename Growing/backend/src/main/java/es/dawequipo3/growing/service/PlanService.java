package es.dawequipo3.growing.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.DataFormatException;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Completed_planRepository completed_planRepository;


    public void save(Plan plan){
        planRepository.save(plan);
    }

    public List<Plan> findPlansByCategory(String name){
        return planRepository.findPlansByCategory_Name(name);
    }

    public Optional<Plan> findPlanByName(String name){return planRepository.findPlansByName(name);}

    public Plan findPlanByAbbr(String abbr){
        return planRepository.findPlansByAbv(abbr).orElseThrow();
    }
    public void saveCompletedPlan(User user, Plan plan){
        completed_planRepository.save(new Completed_plan(user, plan));
    }

    public List<Plan> GetPageable(int page){
        Pageable pageable = PageRequest.of(page, 4);
        return planRepository.findAll(pageable).getContent();
    }

    public List<Plan> likedplans(String email, String cat){
        return planRepository.getPlanByCategoryAndAndLikedUser(cat, email);
    }

    public Boolean existsLiked(String plan, String user){
        return planRepository.existsLiked(plan, user) == 1;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public List<Plan> findPlanFromLikedCategories(User user){
        List<String> categoryNames= new ArrayList<>();
        for (Category category: user.getUserFavoritesCategory()){
            categoryNames.add(category.getName());
        }
        return this.planRepository.getLikedPlanFromCategory(categoryNames);
    }
}