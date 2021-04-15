package es.dawequipo3.growing.controllerREST;

import com.fasterxml.jackson.annotation.JsonView;

import es.dawequipo3.growing.controllerREST.requestBody.CategoryRequest;
import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.Plan;
import es.dawequipo3.growing.model.Tree;
import es.dawequipo3.growing.model.User;

import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api/categories")
public class RESTCategory {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    interface CategoryDetails extends Category.Trees, Category.Basic, Category.Plans, Tree.Basic, Plan.Basic, User.Basic {
    }

    @Operation(summary = "Get the information of all existing categories")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories retrieved correctly",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDetails.class)
                    )}
            ),
    })
    @JsonView(CategoryDetails.class)
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
    public ResponseEntity<Category> categoryInfo(@RequestParam String name, HttpServletRequest request) {
        Optional<Category> op = categoryService.findByName(name);
        if (op.isPresent()) {
            Category category = op.get();
            if (request.getUserPrincipal() != null) {
                String email = request.getUserPrincipal().getName();
                User user = userService.findUserByEmail(email).orElseThrow();
                category.setLikedByUser(user.getUserFavoritesCategory().contains(category));
            }
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
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
            ),
    })
    @JsonView(CategoryDetails.class)
    @PutMapping("/image")
    public ResponseEntity<Category> uploadImage(@RequestParam String name, @RequestParam MultipartFile imageFile) throws SQLException, IOException {
        Optional<Category> op = categoryService.findByName(name);
        if (op.isPresent()) {
            Category category = op.get();
            if (imageFile != null) {
                if (!imageFile.isEmpty()) {
                    category.setIcon(BlobProxy.generateProxy(
                            imageFile.getInputStream(), imageFile.getSize()));
                }
            }
            categoryService.update(category);
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Create a category with a specific name, description, color and an optional image")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created correctly",
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
                    responseCode = "409",
                    description = "There is already a category with the name given by parameter",
                    content = @Content
            ),
    })
    @JsonView(CategoryDetails.class)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        String name = categoryRequest.getName();
        String des = categoryRequest.getDescription();
        String color = categoryRequest.getColor();

        if (!categoryService.existsByName(name)) {
            Category category = new Category(name, des, color);
            categoryService.save(category);
            URI location = URI.create("https://localhost:8443/api/categories?name=".concat(category.getName().replaceAll(" ", "%20")));

            return ResponseEntity.created(location).body(category);
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
            ),
    })
    @GetMapping("/image")
    public ResponseEntity<Object> getImage(@RequestParam String categoryName) throws SQLException {
        Optional<Category> op = categoryService.findByName(categoryName);
        if (op.isPresent()) {
            Category category = op.get();
            if (category.getIcon() != null) {

                Resource file = new InputStreamResource(category.getIcon().getBinaryStream());

                return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(category.getIcon().length()).body(file);

            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
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
    @JsonView(CategoryDetails.class)
    @PutMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> editCategory(@RequestParam String categoryName, @RequestBody CategoryRequest categoryRequest) throws IOException {

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
    @JsonView(CategoryDetails.class)
    @PutMapping("/dislike")
    public ResponseEntity<Category> dislikeCategory(@RequestParam String categoryName, HttpServletRequest request) {

        try {
            Category category = categoryService.dislikeCategory(categoryName, request);
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
    @JsonView(CategoryDetails.class)
    @PutMapping("/like")
    public ResponseEntity<Category> likeCategory(@RequestParam String categoryName, HttpServletRequest request) {
        try {
            Category category = categoryService.likeCategory(categoryName, request);
            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}