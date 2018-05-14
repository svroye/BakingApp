package com.example.steven.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.steven.bakingapp.Objects.Ingredient;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by Steven on 12/05/2018.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Recipe recipe;

    public ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeString = sharedPref.getString("recipekey", "");
        if (!recipeString.equals("")) {
            Gson gson = new Gson();
            recipe = gson.fromJson(recipeString, new TypeToken<Recipe>() {
            }.getType());
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (recipe == null) return 0;
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (recipe == null) return null;
        Ingredient ingredient = recipe.getIngredients().get(position);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if (ingredient.getQuantity() == Math.floor(ingredient.getQuantity())) {
                views.setTextViewText(R.id.grid_item_ingredient,
                        context.getString(R.string.ingredient_quantity_measure_int,
                        (int) ingredient.getQuantity(), ingredient.getMeasure(),
                        ingredient.getName()));
        } else {
            views.setTextViewText(R.id.grid_item_ingredient,
                    context.getString(R.string.ingredient_quantity_measure_float,
                    ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getName()));
        }
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
