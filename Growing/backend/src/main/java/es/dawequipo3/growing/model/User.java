package es.dawequipo3.growing.model;


import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.TreeService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "LikedPlans",
            joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "planName"))
    private List<Plan> likedPlans;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favoriteCategories",
            joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "category"))
    private List<Category> userFavoritesCategory;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Tree> trees;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    List<Completed_plan> completed_plans;

    public User() {}

    public User(String email, String username, String name, String surname, String password, String confirmPassword) {
        super();
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.trees=new ArrayList<Tree>();
    }
    //This constructor is for creating sample users
    public User(String email, String username, String name, String surname, String password) {
        super();
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.trees=new ArrayList<Tree>();

    }

    public void initializeAllTrees(CategoryService categoryService, TreeService treeService){
        for (Category category: categoryService.findAll()) {
            treeService.save(new Tree(this, category));
        }
    }
    public void addCategoryTree(Category category,TreeService treeService){
        treeService.save(new Tree(this, category));
    }

    public void FavoriteCategory(Category category) {
        this.userFavoritesCategory.add(category);
    }
    public boolean CategoryNameInTrees(Category category){
        String name= category.getName();
        for (Tree tree:this.trees){
            if (tree.getCategory().getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Plan> getLikedPlans() {
        return likedPlans;
    }

    public void setLikedPlans(List<Plan> likedPlans) {
        this.likedPlans = likedPlans;
    }

    public List<Category> getUserFavoritesCategory() {
        return userFavoritesCategory;
    }

    public void setUserFavoritesCategory(List<Category> fav_categories) {
        this.userFavoritesCategory = fav_categories;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public void setTrees(List<Tree> trees) {
        this.trees = trees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}