package es.dawequipo3.growing.controllerREST;

import org.springframework.web.multipart.MultipartFile;

public class CategoryRequestBody {
    private String name;
    private String description;
    private String color;
    private MultipartFile imageFile;


    public CategoryRequestBody(String name, String description, String color, MultipartFile imageFile) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.imageFile = imageFile;
    }

    public CategoryRequestBody(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.imageFile = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}