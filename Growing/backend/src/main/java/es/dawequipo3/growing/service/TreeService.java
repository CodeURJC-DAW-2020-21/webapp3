package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.*;
import es.dawequipo3.growing.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreeService {

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EmailService emailService;


    public Optional<Tree> findTree(String email, String categoryName){
        User user = userService.findUserByEmail(email).orElseThrow();
        Category category = categoryService.findByName(categoryName).orElseThrow();
        return treeRepository.findTreeByTreePK(new TreePK(user.getEmail(), category.getName()));
    }

    public void save(Tree tree){
        treeRepository.save(tree);
    }

    public void updateHeight(Tree tree, Plan plan, String email){
        int increment = plan.getDifficulty() * 5;
        if (tree.getHeight() < 45 && tree.getHeight()+ increment >= 45)
            emailService.sendEmailHeight(email, tree.getTreePK().getCategory(), tree.getHeight() + increment);
        tree.setHeight(tree.getHeight() + increment);
        treeRepository.save(tree);
    }

}
