package es.dawequipo3.growing.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.CategoryRepository;
import es.dawequipo3.growing.repository.TreeRepository;
import es.dawequipo3.growing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TreeService treeService;

    @PostConstruct
    public void init(){

        Category mentalHealth = new Category("Mental health", "Because we know that having a good mental health is fundamental, we want to share with you some\n" +
                "                        tasks for helping you to achieve it", "ri-mental-health-line icon", "blue");
        this.save(mentalHealth);

        Category physicalHealth = new Category("Physical health", "Do you want to be fit making regular exercise but you are a lazy person? Look at the plans of\n" +
                "                        this category and reject the lazyness!", "ri-run-line icon", "red");
        categoryRepository.save(physicalHealth);

        Category savings = new Category("Savings", "Everyone want to have money but most of the people waste it in stuff they don't need. In this\n" +
                "                        category we will help you with some tips\n" +
                "                        to increase your savings and not wasting them.", "ri-money-euro-circle-line icon", "green");
        categoryRepository.save(savings);

        Category goodNight = new Category("Good night", "Do you have insomnia? Here you will see multiple tasks that you can do to sleep like a baby\n" +
                "                        during the night", "ri-hotel-bed-fill icon", "bluenavy");
        categoryRepository.save(goodNight);

        Category home = new Category("Home", "Do you have to do home tasks but you leave them to the end or you even make them? This is your\n" +
                "                        category! You won't forget them and finish them quickly", "ri-home-2-line icon", "orange");
        categoryRepository.save(home);

        categoryRepository.save(new Category("Focus and Effort", "Do you want to be challenged daily? I challenge you to follow the pace of this category and\n" +
                "                        complete the refreshing daily challenge", "ri-focus-2-line icon", "purple"));
    }

    public void save(Category category){
            categoryRepository.save(category);
            for (User user: userService.findAll()) {
                treeService.save(new Tree(user, category));
            }
        }


    public List<Category> findAll(){return categoryRepository.findAll();}

    public Optional<Category> findByName(String name){
        return categoryRepository.findCategoryByName(name);
    }

    public void refreshDate(Category category){
        category.setDate(new Date());
        categoryRepository.save(category);
    }

}
