package com.example.steven.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.steven.bakingapp.Objects.Recipe;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailFragment.OnListItemClickListener{

    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity == null) finish();
        // get the recipe object from the intent and change the title of the activity to the name
        // of the recipe
        if (intentThatStartedActivity.hasExtra(getString(R.string.recipe_key))){
            recipe = intentThatStartedActivity.getParcelableExtra(this.getString(R.string.recipe_key));
        }
        setTitle(recipe.getName());

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(this.getString(R.string.ingredients_key), recipe.getIngredients());
        bundle.putParcelableArrayList(this.getString(R.string.steps_key), recipe.getSteps());
        recipeDetailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.activityDetail_framelayout, recipeDetailFragment)
                .commit();
    }

    @Override
    public void onRecipeStepClickListener(int position) {
        Intent intentToStepActivity = new Intent(this, RecipeStepActivity.class);
        intentToStepActivity.putExtra(getString(R.string.recipe_key), recipe);
        intentToStepActivity.putExtra(getString(R.string.position_key), position);
        startActivity(intentToStepActivity);
    }
}
