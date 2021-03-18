package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.ChartData;
import es.dawequipo3.growing.model.User;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.TreeService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@RestController
public class RestCharts {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlanService planService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private UserService userService;

    @Autowired
    private Completed_planRepository completed_planRepository;



    @RequestMapping("/generateBarChart")
    public ArrayList<ChartData> getBarChart(HttpServletRequest request){
        ArrayList<ChartData> categories = new ArrayList<>();
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(),
                    treeService.findTree(user.getEmail(), category.getName()).orElseThrow().getHeight()));
        }
        return categories;
    }

    @RequestMapping("/generateDoughnutChart")
    public ArrayList<ChartData> getDouhnutChart(HttpServletRequest request){
        ArrayList<ChartData> categories = new ArrayList<>();
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(),planService.likedplans(user.getEmail(),category.getName()).size()));
        }
        return categories;
    }

    @RequestMapping("/generateRadarChart")
    public ArrayList<ChartData> getRadarChart(HttpServletRequest request){
        ArrayList<ChartData> categories = new ArrayList<>();
        String username = request.getUserPrincipal().getName();
        User user = userService.findUserByName(username).orElseThrow();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(),
                   completed_planRepository.countTasksDoneByUserAndCategory(user.getEmail(), category.getName())));
        }
        return categories;
    }


}