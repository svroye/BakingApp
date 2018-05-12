package com.example.steven.bakingapp.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.example.steven.bakingapp.R;
import com.example.steven.bakingapp.RecipeDetailActivity;
import com.example.steven.bakingapp.RecipesOverviewActivity;

/**
 * Created by Steven on 11/05/2018.
 */

public class RecipeService extends IntentService {

    public static final String OPEN_RECIPE_ACTION = "com.example.steven.bakingapp.open_recipe_action";
    public static final String UPDATE_RECIPE_WIDGET = "com.example.steven.bakingapp.update_recipe_widget";


    public RecipeService() {
        super("RecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            String action = intent.getAction();
            if (action.equals(OPEN_RECIPE_ACTION)){
                handleOpenRecipeAction(getApplicationContext());
            } else if (action.equals(UPDATE_RECIPE_WIDGET)){
                //handleUpdateWidget(getApplicationContext(), recipe);
            }
        }
    }

    private void handleUpdateWidget(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

    }

    private void handleOpenRecipeAction(Context context){
        Intent intent = new Intent(context, RecipesOverviewActivity.class);
        context.startActivity(intent);

    }
}
