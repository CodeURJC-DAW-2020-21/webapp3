package es.dawequipo3.growing.service;

import java.util.List;
import java.util.Optional;

import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void init() {

        Plan plan1 = new Plan("Meditation", "This is for meditate", 3);
        Plan plan2 = new Plan("Football", "This is for football", 1);

        plan1.setCategory(categoryService.findByName("Mental health").orElseThrow());
        plan2.setCategory(categoryService.findByName("Mental health").orElseThrow());

        planRepository.save(plan1);
        planRepository.save(plan2);

    }

    public void save(Plan plan){
        planRepository.save(plan);
    }

    public List<Plan> findPlansByCategory(String name){
        return planRepository.findPlansByCategory_Name(name);
    }

    public Optional<Plan> findPlansByName(String name){
        return planRepository.findByName(name);
    }
    public List<Plan> findAll() {
        return planRepository.findAll();
    }
}