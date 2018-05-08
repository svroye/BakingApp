package com.example.steven.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.Ingredient;

import java.util.ArrayList;

/**
 * Created by Steven on 8/05/2018.
 */

public class RecipeIngredientsFragment extends Fragment {

    // holds the ingredients for the selected ingredient
    ArrayList <Ingredient> recipeIngredients;

    // TextView holding the ingredients
    TextView recipeTv;

    /**
     * Mandatory empty constructor for the fragment
     */
    public RecipeIngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the View from the corresponding layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        // get the ingredients ArrayList that was passed when the fragment was created
        if (getArguments() != null) {
            recipeIngredients = getArguments().getParcelableArrayList(getString(R.string.ingredients_key));
        }

        // get a reference to the TextView
        recipeTv = rootView.findViewById(R.id.fragmentRecipeIngredients_ingredientsTv);

        // check whether the ingredients were already stored in the Bundle, e.g. after rotation
        if (savedInstanceState != null) {
            recipeTv.setText(savedInstanceState.getString(getString(R.string.recipe_content_key)));
        } else {
            // loop through the ingredients and add them to the TextView. First check whether the quantity
            // is an integer or a double, in order to know how to display it
            for (Ingredient ingredient : recipeIngredients) {
                String ingredientLine;
                if (ingredient.getQuantity() == Math.floor(ingredient.getQuantity())) {
                    ingredientLine = getString(R.string.ingredient_quantity_measure_int, (int) ingredient.getQuantity(),
                            ingredient.getMeasure(), ingredient.getName());
                } else {
                    ingredientLine = getString(R.string.ingredient_quantity_measure_float, ingredient.getQuantity(),
                            ingredient.getMeasure(), ingredient.getName());
                }

                recipeTv.append(ingredientLine + "\n");
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save the state of the recipe TextView e.g. after rotation
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.recipe_content_key), recipeTv.getText().toString());
    }
}
