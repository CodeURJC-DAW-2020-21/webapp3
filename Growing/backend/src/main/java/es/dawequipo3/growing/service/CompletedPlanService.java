package es.dawequipo3.growing.service;


import es.dawequipo3.growing.model.Completed_plan;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.Completed_planRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CompletedPlanService {

    @Autowired
    Completed_planRepository completed_planRepository;

    @Autowired
    UserService userService;

    @Autowired
    PlanService planService;

    @PostConstruct
    public void init() throws InterruptedException {
        Plan plan = planService.findAll().get(0);
        User user = userService.findAll().get(0);

        Completed_plan cp = new Completed_plan(user,plan);
        completed_planRepository.save(cp);
        Thread.sleep(4000);
        cp = new Completed_plan(user,plan);
        completed_planRepository.save(cp);

        List<Completed_plan> results= getCompletedPlanPageByNameSortedByDate(user.getEmail());

    }
    public void save(Completed_plan completed_plan){
        this.save(completed_plan);
    }

    public List<Completed_plan> getCompletedPlanPageByNameSortedByDate(String name) {
        Pageable pageable = PageRequest.of(0, 1);
        Optional<User> user = userService.findUserByEmail(name);
        if (user.isPresent()) {
            return completed_planRepository.getCompleted_planByUserOrderByDateDesc(user.get(), pageable);
        }else{
            return Collections.emptyList();
        }
    }
}
