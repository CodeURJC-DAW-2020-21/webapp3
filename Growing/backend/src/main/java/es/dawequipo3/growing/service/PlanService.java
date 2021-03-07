package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Plan;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PlanService {
    
    private ConcurrentMap<String, Plan> plans = new ConcurrentHashMap<>();


    public PlanService(){
        save(new Plan("Take time to yourself","We know that sometimes life is hard, so there are times when it is necessary to think\n" +
                "                                about ourselves. 30 minutes to do something that you are passionate about will be enough\n" +
                "                                to continue with your duties.",false,3));

        save(new Plan("Meditation","When we meditate, we inject far-reaching and long-lasting benefits into our lives: We\n" +
                "                                lower our stress levels, we get to know our pain, we connect better, we improve our\n" +
                "                                focus, and we're kinder to ourselves. Let us walk you through the basics in our new\n" +
                "                                mindful guide on how to meditate.",false,1));

        save(new Plan("Do yoga","Practicing the postures, breathing exercises and meditation makes you healthier in body,\n" +
                "                                mind and spirit. Yoga lets you tune in, chill out, shape up -- all at the same time.",false,2));


    }

    public void save(Plan Plan){
        this.plans.put(Plan.getName(), Plan);
    }

    public Collection<Plan> findAll() {
        return plans.values();
    }

    public Plan findByName(String name) {
        return plans.get(name);
    }
}
