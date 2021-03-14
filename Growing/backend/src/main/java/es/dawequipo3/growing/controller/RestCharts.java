package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.ChartData;
import es.dawequipo3.growing.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class RestCharts {

    @Autowired
    private CategoryService categoryService;

    Random random = new Random();

    @RequestMapping("/generateBarChart")
    public ArrayList<ChartData> getBarChart(){
        ArrayList<ChartData> categories = new ArrayList<>();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(), random.nextInt(20)));
        }
        return categories;
    }

    @RequestMapping("/generateDoughnutChart")
    public ArrayList<ChartData> getDouhnutChart(){
        ArrayList<ChartData> categories = new ArrayList<>();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(), random.nextInt(20)));
        }
        return categories;
    }

    @RequestMapping("/generateRadarChart")
    public ArrayList<ChartData> getRadarChart(){
        ArrayList<ChartData> categories = new ArrayList<>();
        for (Category category: categoryService.findAll()){
            categories.add(new ChartData(category.getName(), category.getColor(), random.nextInt(20)));
        }
        return categories;
    }


}