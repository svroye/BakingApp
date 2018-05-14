package com.example.steven.bakingapp.Widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.R;
import com.example.steven.bakingapp.RecipeDetailActivity;
import com.example.steven.bakingapp.RecipesOverviewActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_view);
        // set the intent for launching the service that updates the ListView
        Intent serviceIntent = new Intent(context, RecipeRemoteViewsService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // set the adapter and empty view
        views.setRemoteAdapter(R.id.widget_list_view, serviceIntent);
        views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view);

        // get the recipe stored in the SharedPreferences, if any
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeString = preferences.getString(context.getString(R.string.sp_recipe_key), "");
        if (recipeString.equals("")) {
            // no recipe stored, set the empty view for the widget
            views.setTextViewText(R.id.widget_recipeName, context.getString(R.string.widget_title_empty_state));
            // set intent to open RecipeOverview activity
            Intent activityIntent = new Intent(context, RecipesOverviewActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        } else {
            // transform the recipe string back to a Recipe object
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(recipeString, new TypeToken<Recipe>() {
            }.getType());
            views.setTextViewText(R.id.widget_recipeName, recipe.getName());
            Intent activityIntent = new Intent(context, RecipeDetailActivity.class);
            activityIntent.putExtra("recipe", recipe);

            PendingIntent p = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(activityIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, p);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        // update the widget when a broadcast is received. This happens when the user selects a new
        // recipe in the list
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisAppWidget = new ComponentName(context.getApplicationContext(), RecipeWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

