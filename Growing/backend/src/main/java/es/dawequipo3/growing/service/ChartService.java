package es.dawequipo3.growing.service;


import es.dawequipo3.growing.model.Charts;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {

    public Charts getChartsInfo(){
        Charts chartsResponse = new Charts();
        chartsResponse.setChartName("Test app 1");

        List<String> categories = new ArrayList<>();
        categories.add("January");
        categories.add("February");
        categories.add("March");
        categories.add("April");
        categories.add("May");
        categories.add("June");
        categories.add("July");
        chartsResponse.setCategories(categories);

        List<Integer> datasets = new ArrayList<>();
        List<Integer> value = new ArrayList<>();
        value.add(65);
        value.add(59);
        value.add(80);
        value.add(81);
        value.add(56);
        value.add(55);
        value.add(40);

        chartsResponse.setProgress(datasets);

        return chartsResponse;
    }

}
