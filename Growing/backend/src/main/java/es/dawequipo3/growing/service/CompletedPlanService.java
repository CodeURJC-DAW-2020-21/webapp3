package es.dawequipo3.growing.service;


import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.Completed_planRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CompletedPlanService {

    @Autowired
    Completed_planRepository completed_planRepository;

    @Autowired
    UserService userService;

    @Autowired
    PlanService planService;


    public void save(Completed_plan completed_plan){
        completed_planRepository.save(completed_plan);
    }

    public void DeleteCompletedPlan(String email,String name,long date){
        User user= userService.findUserByEmail(email).orElseThrow();
        Plan plan= planService.findPlanByName(name).orElseThrow();
        completed_planRepository.deleteCompleted_planByUserAndPlanAndDate(user,plan,date);
    }

    public List<Completed_plan> getCompletedPlanPageByEmailSortedByDate(String email) {
        Pageable pageable = PageRequest.of(0, 10);
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            return completed_planRepository.getCompleted_planByUserOrderByDateDesc(user.get(), pageable);
        }else{
            return Collections.emptyList();
        }
    }
}
