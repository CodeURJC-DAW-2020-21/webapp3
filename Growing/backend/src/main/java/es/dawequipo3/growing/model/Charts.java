package es.dawequipo3.growing.model;

import java.util.List;

public class Charts {
    private String chartName;
    private List<String> categories;
    private List<Integer> progress;

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Integer> getProgress() {
        return progress;
    }

    public void setProgress(List<Integer> progress) {
        this.progress = progress;
    }
}
