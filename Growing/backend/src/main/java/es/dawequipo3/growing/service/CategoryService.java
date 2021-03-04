package es.dawequipo3.growing.service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import es.dawequipo3.growing.model.Category.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private ConcurrentMap <String, Category> categories = new ConcurrentHashMap<>();


    public CategoryService(){
        save(new Category("Mental health", "Because we know that having a good mental health is fundamental, we want to share with you some\n" +
                "                        tasks for helping you to achieve it", "ri-mental-health-line icon", "blue"));
        save(new Category("Physical health", "Do you want to be fit making regular exercise but you are a lazy person? Look at the plans of\n" +
                "                        this category and reject the lazyness!", "ri-run-line icon", "red"));
        save(new Category("Savings", "Everyone want to have money but most of the people waste it in stuff they don't need. In this\n" +
                "                        category we will help you with some tips\n" +
                "                        to increase your savings and not wasting them.", "ri-money-euro-circle-line icon", "green"));
        save(new Category("Good night", "Do you have insomnia? Here you will see multiple tasks that you can do to sleep like a baby\n" +
                "                        during the night", "ri-hotel-bed-fill icon", "bluenavy"));
        save(new Category("Home", "Do you have to do home tasks but you leave them to the end or you even make them? This is your\n" +
                "                        category! You won't forget them and finish them quickly", "ri-home-2-line icon", "orange"));
        save(new Category("Focus and Effort", "Do you want to be challenged daily? I challenge you to follow the pace of this category and\n" +
                "                        complete the refreshing daily challenge.", "ri-focus-2-line icon", "purple"));

    }

    public void save(Category category){
        this.categories.put(category.getName(), category);
    }

    public Collection<Category> findAll() {
        return categories.values();
    }

    public Category findByName(String name) {
        return categories.get(name);
    }


}
