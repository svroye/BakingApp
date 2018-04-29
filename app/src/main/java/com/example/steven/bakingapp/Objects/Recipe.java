package com.example.steven.bakingapp.Objects;

import java.util.ArrayList;

/**
 * Created by Steven on 29/04/2018.
 */

public class Recipe {

    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> steps;

    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }
}
