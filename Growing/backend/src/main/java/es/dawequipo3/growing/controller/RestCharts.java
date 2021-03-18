package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.ChartData;
import es.dawequipo3.growing.model.CompletedPlanPK;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.TreeService;
import es.dawequipo3.growing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class RestCharts {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PlanService planService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private Completed_planRepository completed_planRepository;



    @RequestMapping("/generateBarChart")
    public ArrayList<ChartData> getBarChart(){
        ArrayList<ChartData> categories = new ArrayList<>();

        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(),
                    treeService.findTree("p1@gmail.com", category.getName()).orElseThrow().getHeight()));
        }
        return categories;
    }

    @RequestMapping("/generateDoughnutChart")
    public ArrayList<ChartData> getDouhnutChart(){
        ArrayList<ChartData> categories = new ArrayList<>();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(),planService.likedplans("p1@gmail.com",category.getName()).size()));
        }
        return categories;
    }

    @RequestMapping("/generateRadarChart")
    public ArrayList<ChartData> getRadarChart(){
        ArrayList<ChartData> categories = new ArrayList<>();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(),
                   completed_planRepository.countTasksDoneByUserAndCategory("p1@gmail.com", category.getName())));
        }
        return categories;
    }


}