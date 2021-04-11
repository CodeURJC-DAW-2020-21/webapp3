package es.dawequipo3.growing.controllerREST;



public class PlanCreateRequest {

    private String category;
    private String planName;
    private String abv;
    private String description;
    private int difficulty;

    public PlanCreateRequest(String category, String planName, String abv, String description, int difficulty) {
        this.category = category;
        this.planName = planName;
        this.abv = abv;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
