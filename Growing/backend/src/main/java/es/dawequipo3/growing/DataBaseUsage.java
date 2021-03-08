package es.dawequipo3.growing;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class DataBaseUsage implements CommandLineRunner {

    @Autowired
    private CategoryRepository repository;

    @Override
    public void run(String[] args) throws Exception {
        repository.save(new Category("Mental health", "Because we know that having a good mental health is fundamental, we want to share with you some\n" +
                "                        tasks for helping you to achieve it", "ri-mental-health-line icon", "blue"));
    }

}
