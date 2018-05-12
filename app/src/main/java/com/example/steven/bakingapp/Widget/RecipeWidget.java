package com.example.steven.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.Ingredient;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.R;
import com.example.steven.bakingapp.RecipeDetailActivity;
import com.example.steven.bakingapp.RecipesOverviewActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    private Recipe recipe;

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if (recipe == null) {
            setEmptyRecipeState(views, context);
        } else {
            setSelectedRecipeState(views, context);
        }

        // Instruct the widget manager to update the widget

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("RECIPEWIDGET", "INSIDE ONUPDATE");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent != null) {
            if (intent.hasExtra("recipe")){
                recipe = intent.getParcelableExtra("recipe");
                Log.d("RECIPEWIDGET", recipe.getName());

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(),
                        RecipeWidget.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

                onUpdate(context, appWidgetManager, appWidgetIds);
            }
        }
    }

    public void setEmptyRecipeState(RemoteViews views, Context context){
        views.setTextViewText(R.id.widget_recipeName, "No recipe selected yet.");
        views.setTextViewText(R.id.widget_recipeIngredients, "Select one of the recipes in Baking App" +
                " to see the list of ingredients here.");

        Intent intent = new Intent(context, RecipesOverviewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
    }

    public void setSelectedRecipeState(RemoteViews views, Context context){
        views.setTextViewText(R.id.widget_recipeName, recipe.getName());
        String recipeList = "";

        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.getQuantity() == Math.floor(ingredient.getQuantity())) {
                recipeList += context.getString(R.string.ingredient_quantity_measure_int,
                        (int) ingredient.getQuantity(), ingredient.getMeasure(),
                        ingredient.getName()) + "\n";
            } else {
                recipeList += context.getString(R.string.ingredient_quantity_measure_float,
                        ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getName())
                        + "\n";
            }
        }

        views.setTextViewText(R.id.widget_recipeIngredients, recipeList);

        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(context.getString(R.string.recipe_key), recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
    }
}

