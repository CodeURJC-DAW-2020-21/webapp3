package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Optional;

@Service
public class TreeService {

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    public Optional<Tree> findTree(String email, String categoryName){
        User user = userService.findUserByEmail(email).orElseThrow();
        Category category = categoryService.findByName(categoryName).orElseThrow();
        return treeRepository.findTreeByTreePK(new TreePK(user.getEmail(), category.getName()));
    }

    public void save(Tree tree){
        treeRepository.save(tree);
    }

    public void UpdateTreeNewPlan(Tree tree,Plan plan){
        int increase = (int) Math.pow(plan.getDifficulty(),2);
        int newHeight= tree.getHeight()+increase;
        tree.setHeight(newHeight);
    }

    public void UpdateTreeRemovePlan(Tree tree,Plan plan){
        int decrease = (int) Math.pow(plan.getDifficulty(),2);
        int newHeight= tree.getHeight()-decrease;
        tree.setHeight(newHeight);
    }
}
