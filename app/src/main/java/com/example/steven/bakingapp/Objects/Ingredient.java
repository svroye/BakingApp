package com.example.steven.bakingapp.Objects;

/**
 * Created by Steven on 29/04/2018.
 */

public class Ingredient {

    private String name;
    private String measure;
    private double quantity;

    public Ingredient(String name, String measure, double quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
