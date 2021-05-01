package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;

import es.dawequipo3.growing.controllerREST.requestBody.CategoryRequest;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;

import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.ImageService;
import es.dawequipo3.growing.service.TreeService;
import es.dawequipo3.growing.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
@RequestMapping("/api/categories")
public class RESTCategory {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private TreeService treeService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    interface CategoriesDetails extends Category.Basic {
    }

    interface CategoryDetails extends Category.Basic, Category.Registered, Category.Plans, Tree.Basic, Plan.Basic, Plan.Registered, User.Basic {
    }

    interface UserRegisteredCategoryDetails extends Category.Registered, Category.Basic, Category.Plans, Tree.Basic, Plan.Basic, Plan.Registered, User.Basic {
    }

    @Operation(summary = "Get the information of all existing categories")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories retrieved correctly",
                    content = {@Content(
                            schema = @Schema(implementation = CategoriesDetails.class)
                    )}
            ),
    })
    @JsonView(CategoriesDetails.class)
    @GetMapping("/")
    public ResponseEntity<Collection<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }


    @Operation(summary = "Get the information a specific category searched by its name")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category's information retrieved correctly",
                    content = {@Content(
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            ),
    })
    @JsonView(CategoryDetails.class)
    @GetMapping("")
    public ResponseEntity<ArrayList<Object>> categoryInfo(@RequestParam String name, HttpServletRequest request) {
        Optional<Category> op = categoryService.findByName(name);
        if (op.isPresent()) {
            Category category = op.get();
            Principal optionalUser = request.getUserPrincipal();
            ArrayList<Object> categories = new ArrayList<>();
            if (optionalUser != null) {
                String email = request.getUserPrincipal().getName();
                User user = userService.findUserByEmail(email).orElse(null);
                category.setLikedByUser(user.getUserFavoritesCategory().contains(category));
                Optional<Tree> tree = treeService.findTree(email, name);
                categories.add(tree);

                for(Plan plan : category.getPlans()){
                    plan.setLikedUser(user.getLikedPlans().contains(plan));
                }
            }
            categories.add(category);
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Changes the current icon with a new one indicated by the user")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category's icon changed correctly",
                    content = {@Content(
                            mediaType = "image/*",
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permission error, only access to admin account",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Image not found",
                    content = @Content
            )
    })
    @JsonView(CategoryDetails.class)
    @PutMapping("/image")
    public ResponseEntity<Category> uploadImage(@RequestParam String name, @RequestParam MultipartFile imageFile) throws IOException {
        return imageService.uploadImage(name, imageFile);
    }

    @Operation(summary = "Create a category with a specific name, description, color and an optional image. Color " +
            "must be green, orange, darkblue, red, purple or blue")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created correctly",
                    content = {@Content(
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request, the color is not valid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permission error, only access to admin account",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "There is already a category with the name given by parameter",
                    content = @Content
            )
    })

    @JsonView(CategoryDetails.class)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        String name = categoryRequest.getName();
        String des = categoryRequest.getDescription();
        String color = categoryRequest.getColor();

        if (!categoryService.existsByName(name)) {
            if (color.equals("green") || color.equals("orange") || color.equals("darkblue") || color.equals("red") || color.equals("purple") || color.equals("blue")) {

                Category category = new Category(name, des, color);
                categoryService.save(category);
                URI location = URI.create("https://localhost:8443/api/categories?name=".concat(category.getName().replaceAll(" ", "%20")));

                return ResponseEntity.created(location).body(category);
            } else return ResponseEntity.badRequest().build();
        } else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Operation(summary = "Returns the current user selected category icon")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category's icon retrieved correctly",
                    content = {@Content(
                            mediaType = "image/*"
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Image not found",
                    content = @Content
            )
    })

    @GetMapping("/image")
    public ResponseEntity<Object> getImage(@RequestParam String categoryName) throws SQLException {
        return imageService.downloadCategoryImage(categoryName);
    }

    @Operation(summary = "Edits an existing category")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category edited correctly",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permission error, only access to admin account",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            )
    })
    @JsonView(CategoriesDetails.class)
    @PutMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> editCategory(@RequestParam String categoryName, @RequestBody CategoryRequest categoryRequest) {

        String newDescription = categoryRequest.getDescription();
        String color = categoryRequest.getColor();

        try {
            Category category = categoryService.editCategory(categoryName, newDescription, color);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Remove the like of a category as the logged user")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Like removed correctly",
                    content = {@Content(
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permission error, only access to logged users",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            )
    })

    @JsonView(UserRegisteredCategoryDetails.class)
    @PutMapping("/notFav")
    public ResponseEntity<Category> dislikeCategory(@RequestParam String categoryName, HttpServletRequest request) {

        try {
            Category category = categoryService.likeCategory(categoryName, request, false);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Like a category as the logged user")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category liked correctly",
                    content = {@Content(
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permission error, only access to logged users",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            )
    })

    @JsonView(UserRegisteredCategoryDetails.class)
    @PutMapping("/fav")
    public ResponseEntity<Category> likeCategory(@RequestParam String categoryName, HttpServletRequest request) {
        try {
            Category category = categoryService.likeCategory(categoryName, request, true);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
