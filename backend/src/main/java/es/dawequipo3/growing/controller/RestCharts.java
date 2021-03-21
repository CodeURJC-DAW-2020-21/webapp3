package es.dawequipo3.growing.controller;

import es.dawequipo3.growing.model.Category;
import es.dawequipo3.growing.model.ChartData;
import es.dawequipo3.growing.repository.Completed_planRepository;
import es.dawequipo3.growing.service.CategoryService;
import es.dawequipo3.growing.service.PlanService;
import es.dawequipo3.growing.service.TreeService;
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
    private Completed_planRepository completed_planRepository;

    /**
     * It loads the bar chart with real current user's data. In this case, the height achieved by the user on
     * each category
     *
     * @param request
     * @return
     */
    @RequestMapping("/generateBarChart")
    public ArrayList<ChartData> getBarChart(HttpServletRequest request) {
        ArrayList<ChartData> categories = new ArrayList<>();
        String email = request.getUserPrincipal().getName();
        for (Category category : categoryService.findAll()) {
            categories.add(new ChartData(category.getName(), category.getColor(),
                    treeService.findTree(email, category.getName()).orElseThrow().getHeight()));
        }
        return categories;
    }

    /**
     * It loads the doughnut chart with real current user's data. In this case, the number of likes given by the user on
     * each category
     *
     * @param request
     * @return
     */
    @RequestMapping("/generateDoughnutChart")
    public ArrayList<ChartData> getDouhnutChart(HttpServletRequest request) {
        ArrayList<ChartData> categories = new ArrayList<>();
        String email = request.getUserPrincipal().getName();
        for (Category category : categoryService.findAll()) {
            categories.add(new ChartData(category.getName(), category.getColor(), planService.likedplans(email, category.getName()).size()));
        }
        return categories;
    }

    /**
     * It loads the radar chart with real current user's data. In this case, the number of tasks done by the user on
     * each category
     *
     * @param request
     * @return
     */
    @RequestMapping("/generateRadarChart")
    public ArrayList<ChartData> getRadarChart(HttpServletRequest request) {
        ArrayList<ChartData> categories = new ArrayList<>();
        String email = request.getUserPrincipal().getName();
        for (Category category : categoryService.findAll()) {
            categories.add(new ChartData(category.getName(), category.getColor(),
                    completed_planRepository.countTasksDoneByUserAndCategory(email, category.getName())));
        }
        return categories;
    }


}