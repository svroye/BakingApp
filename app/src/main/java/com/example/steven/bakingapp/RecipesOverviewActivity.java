package com.example.steven.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.steven.bakingapp.Adapters.RecipesAdapter;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.Utils.NetworkUtils;
import com.example.steven.bakingapp.Utils.RecipesJsonUtils;

import java.util.ArrayList;

/**
 * Main entry point of the application. Displays a list of recipes.
 */
public class RecipesOverviewActivity extends AppCompatActivity
        implements RecipesAdapter.ListItemClickListener {

    // RecyclerView for displaying the individual recipes
    private RecyclerView mRecipesRecyclerView;
    // ArrayList holding the recipes
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // find reference th RecyclerView
        mRecipesRecyclerView = findViewById(R.id.activityRecipes_recyclerView);

        // set attributes for the recycler view
        mRecipesRecyclerView.setHasFixedSize(true);

        // check the orientation. If portrait, use vertical layout manager. If landscape,
        // use grid layout
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
         layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                 false);
        } else {
           layoutManager = new GridLayoutManager(this, 2);
        }

        mRecipesRecyclerView.setLayoutManager(layoutManager);

        // perform the AsyncTask to fetch the Recipes from a network request
        (new FetchRecipesTask()).execute();
    }

    @Override
    public void onListItemClick(int position) {
        // when a recipe is clicked, open the detail activity to display the steps
        // when on tablet, also the description of each step is shown
        Intent intentToDetailActivity = new Intent(RecipesOverviewActivity.this,
                RecipeDetailActivity.class);
        Recipe clickedRecipe = recipes.get(position);
        // add the clicked recipe to the intent
        intentToDetailActivity.putExtra(getString(R.string.recipe_key), clickedRecipe);
        startActivity(intentToDetailActivity);
    }


    private class FetchRecipesTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            // get the recipes from a network request; store them in an ArrayList
            recipes = NetworkUtils.getRecipes(RecipesOverviewActivity.this);
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            super.onPostExecute(recipes);
            // create a new adapter with the recipes and set them to the RecyclerView
            RecipesAdapter adapter = new RecipesAdapter(recipes, RecipesOverviewActivity.this);
            mRecipesRecyclerView.setAdapter(adapter);
        }
    }


}
