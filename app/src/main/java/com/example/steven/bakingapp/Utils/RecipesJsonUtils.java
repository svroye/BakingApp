package com.example.steven.bakingapp.Utils;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.example.steven.bakingapp.Objects.Ingredient;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.Objects.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by Steven on 29/04/2018.
 */

public class RecipesJsonUtils {

    private static final String LOG_TAG = "RecipesJsonUtils";

    private static String readJSONFile(Context context) {
        String jsonResponse = null;

        try {
            InputStream inputStream = context.getAssets().open("recipes.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonResponse = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, jsonResponse);
        return jsonResponse;
    }

    public static ArrayList<Recipe> getRecipes(Context context){
        ArrayList<Recipe> recipesArrayList = new ArrayList<>();

        try {
            // read the JSONArray from the JSON in the asset folder
            JSONArray recipesJSON = new JSONArray(readJSONFile(context));

            // loop through the recipes in the array
            for (int i = 0; i < recipesJSON.length(); i++){
                // each element in the array is a Recipe. Check whether it has the
                // needed attributes and create a Recipe object to add to the ArrayList
                JSONObject recipeJsonObject = recipesJSON.getJSONObject(i);

                // attributes for the Recipe objects in the ArrayList
                String name = null;
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ArrayList<RecipeStep> steps = new ArrayList<>();
                String image = null;

                if (recipeJsonObject.has("name")){
                    name = recipeJsonObject.getString("name");
                }

                if (recipeJsonObject.has("ingredients")){
                    JSONArray ingredientsJSONArray = recipeJsonObject.getJSONArray("ingredients");
                        for (int j = 0; j < ingredientsJSONArray.length(); j++) {
                            JSONObject ingredientJSON = ingredientsJSONArray.getJSONObject(j);

                            // attributes for a single ingredient in the ingredients ArrayList
                            double quantity = 0.0;
                            String measure = null;
                            String ingredient = null;

                            if (ingredientJSON.has("quantity")){
                                quantity = ingredientJSON.getDouble("quantity");
                            }

                            if (ingredientJSON.has("measure")){
                                measure = ingredientJSON.getString("measure");
                            }

                            if (ingredientJSON.has("ingredient")){
                                ingredient = ingredientJSON.getString("ingredient");
                            }

                            ingredients.add(new Ingredient(ingredient, measure, quantity));
                        }
                }

                if (recipeJsonObject.has("steps")){
                    JSONArray stepsJSONArray = recipeJsonObject.getJSONArray("steps");
                    for (int j = 0; j < stepsJSONArray.length(); j++) {
                        JSONObject stepsJSON = stepsJSONArray.getJSONObject(j);

                        // attributes for a single step in the steps ArrayList
                        String shortDescription = null;
                        String fullDescription = null;
                        String videoURL = null;
                        String thumbnailURL = null;
                        int id = -1;

                        if (stepsJSON.has("shortDescription")){
                            shortDescription = stepsJSON.getString("shortDescription");
                        }

                        if (stepsJSON.has("description")){
                            fullDescription = stepsJSON.getString("description");
                        }

                        if (stepsJSON.has("videoURL")){
                            videoURL = stepsJSON.getString("videoURL");
                        }

                        if (stepsJSON.has("thumbnailURL")){
                            thumbnailURL = stepsJSON.getString("thumbnailURL");
                        }

                        if (stepsJSON.has("id")){
                            id = stepsJSON.getInt("id");
                        }

                        steps.add(new RecipeStep(shortDescription, fullDescription,
                                videoURL, thumbnailURL, id));
                    }
                }

                if (recipeJsonObject.has("image")){
                    image = recipeJsonObject.getString("image");
                }

                recipesArrayList.add(new Recipe(name, ingredients, steps, image));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipesArrayList;
    }


}
