package com.example.steven.bakingapp.Utils;

import android.content.Context;

import com.example.steven.bakingapp.Objects.Ingredient;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.Objects.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Steven on 14/05/2018.
 */

public class NetworkUtils {

    private static final String urlString = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getJsonResponse(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlString)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Recipe> getRecipes(Context context){
        ArrayList<Recipe> recipesArrayList = new ArrayList<>();
        String jsonResponse = getJsonResponse();
        if (jsonResponse != null){
            try {
                JSONArray recipesJSON = new JSONArray(jsonResponse);
                // loop through the recipes in the array
                for (int i = 0; i < recipesJSON.length(); i++){
                    // each element in the array is a Recipe. Check whether it has the
                    // needed attributes and create a Recipe object to add to the ArrayList
                    JSONObject recipeJsonObject = recipesJSON.getJSONObject(i);

                    // attributes for the Recipe objects in the ArrayList
                    String name = null;
                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    ArrayList<RecipeStep> steps = new ArrayList<>();

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

                    recipesArrayList.add(new Recipe(name, ingredients, steps));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recipesArrayList;
    }
}
