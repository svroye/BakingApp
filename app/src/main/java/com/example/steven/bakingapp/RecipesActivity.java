package com.example.steven.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.steven.bakingapp.Adapters.RecipesAdapter;
import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.Utils.RecipesJsonUtils;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener {

    private static final String LOG_TAG = "RecipesActivity";
    private RecyclerView mRecipesRecyclerView;
    private ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

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

        recipes = RecipesJsonUtils.getRecipes(this);

        RecipesAdapter adapter = new RecipesAdapter(recipes, this);

        mRecipesRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onListItemClick(int position) {
        Intent intentToDetailActivity = new Intent(RecipesActivity.this,
                RecipeDetailActivity.class);
        Recipe clickedRecipe = recipes.get(position);
        Log.d(LOG_TAG, clickedRecipe.getName());
        intentToDetailActivity.putExtra("recipe", clickedRecipe);
        startActivity(intentToDetailActivity);
    }
}
