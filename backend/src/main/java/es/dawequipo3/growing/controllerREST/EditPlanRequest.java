package es.dawequipo3.growing.controllerREST;


public class EditPlanRequest {

    private String newDescription;
    private String abv;
    private int difficulty;

    public EditPlanRequest(String newDescription, String abv, int difficulty) {
        this.newDescription = newDescription;
        this.abv = abv;
        this.difficulty = difficulty;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public String getAbv() {
        return abv;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
