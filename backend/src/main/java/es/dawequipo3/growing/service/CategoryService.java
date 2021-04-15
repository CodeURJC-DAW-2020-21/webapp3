package es.dawequipo3.growing.service;


import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.CategoryRepository;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private TreeService treeService;


    public void save(Category category) {
        categoryRepository.save(category);
        for (User user : userService.findAll()) {
            treeService.save(new Tree(user, category));
        }
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void editCategory(Category category, String description, String color, MultipartFile imageFile) throws IOException {

        editCategory(category, description, color);
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                category.setIcon(BlobProxy.generateProxy(
                        imageFile.getInputStream(), imageFile.getSize()));
            }
        }
        update(category);

    }

    public Category editCategory(String categoryName, String newDescription, String color) {

        Optional<Category> op = findByName(categoryName);

        Category category = op.orElseThrow();
        if (newDescription == null) {
            newDescription = "";
        }
        if (color == null) {
            color = "";
        }
        editCategory(category, newDescription, color);
        return category;
    }

    public void editCategory(Category category, String description, String color) {
        if (!description.isBlank()) {
            category.setDescription(description);
        }
        if (!color.isBlank()) {
            category.setColor(color);
        }
        update(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsCategoryByName(name);
    }


    public Category dislikeCategory(String categoryName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Category category = findByName(categoryName).orElseThrow();

        user.getUserFavoritesCategory().remove(category);
        category.setLikedByUser(false);
        userService.update(user);
        return category;
    }

    public Category likeCategory(String categoryName, HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Category category = findByName(categoryName).orElseThrow();

        user.getUserFavoritesCategory().add(category);
        category.setLikedByUser(true);
        userService.update(user);
    }
}
