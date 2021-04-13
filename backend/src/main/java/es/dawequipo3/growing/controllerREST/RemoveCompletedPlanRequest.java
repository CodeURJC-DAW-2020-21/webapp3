package es.dawequipo3.growing.controllerREST;

public class RemoveCompletedPlanRequest {
    private String email;
    private String planName;
    private String date;

    public RemoveCompletedPlanRequest(String email, String planName, String date) {
        this.email = email;
        this.planName = planName;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public String getPlanName() {
        return planName;
    }

    public String getDate() {
        return date;
    }
}
