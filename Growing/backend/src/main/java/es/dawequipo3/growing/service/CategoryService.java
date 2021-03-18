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
