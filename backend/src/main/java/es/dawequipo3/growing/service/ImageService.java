package es.dawequipo3.growing.service;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    public ResponseEntity<Object> downloadProfileImage(HttpServletRequest request) throws SQLException {

        String email = request.getUserPrincipal().getName();
        User user = userService.findUserByEmail(email).orElseThrow();

        if (user.getImageFile() != null) {

            Resource file = new InputStreamResource(user.getImageFile().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.getImageFile().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<Object> downloadCategoryImage(@PathVariable String categoryName) throws SQLException {
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


    public ResponseEntity<Category> uploadImage(@RequestParam String name, @RequestParam MultipartFile imageFile) throws IOException {
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

    public ResponseEntity<User> uploadUserProfileImage(HttpServletRequest request, MultipartFile imageFile) throws SQLException, IOException {
        String email = request.getUserPrincipal().getName();
        Optional<User> op = userService.findUserByEmail(email);
        if (op.isPresent()) {
            User user = op.get();
            if (imageFile != null) {
                if (!imageFile.isEmpty()) {
                    user.setImageFile(BlobProxy.generateProxy(
                            imageFile.getInputStream(), imageFile.getSize()));
                }
            }
            userService.update(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }


}
