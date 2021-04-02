package es.dawequipo3.growing.model;

import com.fasterxml.jackson.annotation.JsonView;

public class ChartData {

    public interface Basico{}

    @JsonView(Basico.class)
    private String email;

    @JsonView(Basico.class)
    private String name;

    private String color;

    @JsonView(Basico.class)
    private int data;

    public ChartData(String name, String color, int data) {
        this.name = name;
        this.color = color;
        this.data = data;
    }

    public ChartData(String name, String color) {
        this.name = name;
        this.color = color;
        this.data = 0;
    }

    public ChartData(String email, String name, String color, int data) {
        this.email = email;
        this.name = name;
        this.color = color;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}