package es.dawequipo3.growing.controller;


import es.dawequipo3.growing.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class ImageController {


    @Autowired
    private ImageService imageService;


    /**
     * This method allows the page to display the correspondent image, getting it from the user profile image (user.getImageFile())
     *
     * @param request
     * @return ResponseEntity<Object>
     * @throws SQLException if image not found
     */
    @GetMapping("/image/profile")
    public ResponseEntity<Object> downloadProfileImage(HttpServletRequest request) throws SQLException {
        return imageService.downloadProfileImage(request);
    }

    /**
     * This one is almost the same, only changes the origin of the photo, getting it from the category icon
     *
     * @param categoryName name of the category which its icon will be displayed
     * @return ResponseEntity<Object>
     * @throws SQLException if image not found
     */
    @GetMapping("/image/category/{categoryName}")
    public ResponseEntity<Object> downloadCategoryImage(@PathVariable String categoryName) throws SQLException {
        return imageService.downloadCategoryImage(categoryName);
    }

}
