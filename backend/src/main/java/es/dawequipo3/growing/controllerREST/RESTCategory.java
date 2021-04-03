package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;

import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.UserService;

import org.hibernate.engine.jdbc.BlobProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/api/categories")
public class RESTCategory {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    interface CategoryDetails extends Category.Trees, Category.Basic, Tree.Basic {}

    @JsonView(CategoryDetails.class)
    @GetMapping("/")
    public ResponseEntity<Collection<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @JsonView(CategoryDetails.class)
    @GetMapping("")
    public ResponseEntity<Category> categoryInfo(@RequestParam String name, HttpServletRequest request) {
        Optional<Category> op = categoryService.findByName(name);
        if (op.isPresent()) {
            Category category = op.get();
            if (request.getUserPrincipal() != null){
                String email = request.getUserPrincipal().getName();
                User user = userService.findUserByEmail(email).orElseThrow();
                category.setLikedByUser(user.getUserFavoritesCategory().contains(category));
            }
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @JsonView(CategoryDetails.class)
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> createCategory(@RequestParam String name, @RequestParam String des,
                                                   @RequestParam String color, @RequestParam (required = false) MultipartFile imageFile,
                                                   HttpServletRequest request) throws IOException {
        if (request.getUserPrincipal() != null || request.isUserInRole("USER")){
            if (!categoryService.existsByName(name)) {
                Category category = new Category(name, des, color);
                if (!imageFile.isEmpty()) {
                    category.setIcon(BlobProxy.generateProxy(
                            imageFile.getInputStream(), imageFile.getSize()));
                }
                categoryService.save(category);
                URI location = URI.create("https://localhost:8443/api/categories?name=".concat(category.getName().replaceAll(" ", "%20")));
                return ResponseEntity.created(location).build();
            }
            else return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
       return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @JsonView(CategoryDetails.class)
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> editCategory(@RequestParam String categoryName, @RequestParam String newDescription,
                                                   @RequestParam String color, MultipartFile imageFile) throws IOException {

        Optional<Category> op = categoryService.findByName(categoryName);
        if (op.isPresent()){
            Category category = op.get();
            categoryService.editCategory(category, newDescription, color, imageFile);
            return ResponseEntity.ok(category);
        }
         else return ResponseEntity.notFound().build();
    }

    @JsonView(CategoryDetails.class)
    @PutMapping("/dislike")
    public ResponseEntity<Category> dislikeCategory(@RequestParam String categoryName, HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();
        Optional<Category> op = categoryService.findByName(categoryName);
        if (op.isPresent()){
            Category category = op.get();
            user.getUserFavoritesCategory().remove(category);
            category.setLikedByUser(false);
            userService.update(user);
            return ResponseEntity.ok(category);
        }
         else return ResponseEntity.notFound().build();
    }

    @JsonView(CategoryDetails.class)
    @PutMapping("/like")
    public ResponseEntity<Category> likeCategory(@RequestParam String categoryName, HttpServletRequest request){
        if (request.getUserPrincipal() != null){
            String email = request.getUserPrincipal().getName();
            User user = userService.findUserByEmail(email).orElseThrow();
            Optional<Category> op = categoryService.findByName(categoryName);
            if (op.isPresent()){
                Category category = op.get();
                user.getUserFavoritesCategory().add(category);
                category.setLikedByUser(true);
                userService.update(user);
                return ResponseEntity.ok(category);
            }
            else return ResponseEntity.notFound().build();    
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
