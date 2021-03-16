package es.dawequipo3.growing.service;

import java.util.List;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
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

    @PostConstruct
    public void init() {
        Category mentalHealth=categoryService.findByName("Mental health").orElseThrow();
        Plan plan1 = new Plan("Meditation", "This is for meditate", 3,mentalHealth);
        Plan plan2 = new Plan("Football", "This is for football", 1,mentalHealth);


        planRepository.save(plan1);
        planRepository.save(plan2);

        plan1 = new Plan("oSjp80p0c5", "This is for meditate", 3,mentalHealth);
        plan2 = new Plan("I57eaMlMHq", "This is for football", 1,mentalHealth);


        planRepository.save(plan1);
        planRepository.save(plan2);

        plan1 = new Plan("9a2thgSdiM", "This is for meditate", 3,mentalHealth);
        plan2 = new Plan("CwFqHsDCjN", "This is for football", 1,mentalHealth);


        planRepository.save(plan1);
        planRepository.save(plan2);

        plan1 = new Plan("fPWxhAUw1U", "This is for meditate", 3,mentalHealth);
        plan2 = new Plan("6hDtymXPIe", "This is for football", 1,mentalHealth);


        planRepository.save(plan1);
        planRepository.save(plan2);

        plan1 = new Plan("oDFkp02Osb", "This is for meditate", 3,mentalHealth);
        plan2 = new Plan("bluNuqJV90", "This is for football", 1,mentalHealth);


        planRepository.save(plan1);
        planRepository.save(plan2);

        plan1 = new Plan("vivDTTZe3h", "This is for meditate", 3,mentalHealth);
        plan2 = new Plan("5mQOCUnDPv", "This is for football", 1,mentalHealth);


        planRepository.save(plan1);
        planRepository.save(plan2);

    }

    public void save(Plan plan){
        planRepository.save(plan);
    }

    public List<Plan> findPlansByCategory(String name){
        return planRepository.findPlansByCategory_Name(name);
    }

    public List<Plan> GetPageable(int page){
        Pageable pageable = PageRequest.of(page, 4);
        return planRepository.findAll(pageable).getContent();


    }
    public List<Plan> findAll() {
        return planRepository.findAll();
    }
}