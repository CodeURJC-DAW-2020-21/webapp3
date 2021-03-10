package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.TreePK;
import es.dawequipo3.growing.model.User;
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

    public Optional<Tree> findTree(String email, String categoryName){
        User user = userService.findUserByEmail(email).orElseThrow();
        Category category = categoryService.findByName(categoryName).orElseThrow();
        return treeRepository.findTreeByTreePK(new TreePK(user.getEmail(), category.getName()));
    }

    public void save(Tree tree){
        treeRepository.save(tree);
    }


}
