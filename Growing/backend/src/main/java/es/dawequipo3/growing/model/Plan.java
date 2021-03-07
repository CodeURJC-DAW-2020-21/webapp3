package es.dawequipo3.growing.model;

public class Plan {
    private String name;
    private String description;
    private boolean liked;
    private int difficulty;

    public Plan() {
    }

    public Plan(String name, String description, boolean liked, int difficulty) {
        super();
        this.name = name;
        this.description = description;
        this.liked = liked;
        this.difficulty = difficulty;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", liked=" + liked +
                ", difficulty=" + difficulty +
                '}';
    }
}
