package com.example.steven.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.Recipe;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeStepsFragment.OnListItemClickListener{

    private Recipe recipe;

    private boolean mTwoPane;
    private TextView recipeStepDescriptionTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // check whether the tablet or phone layout was inflated
        if (findViewById(R.id.activityRecipeDetail_detailStepFramelayout) != null) {
            mTwoPane = true;
            recipeStepDescriptionTv = findViewById(R.id.activityRecipeDetail_stepInfoTextView);
        } else {
            mTwoPane = false;
        }

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity == null) finish();
        // get the recipe object from the intent and change the title of the activity to the name
        // of the recipe
        if (intentThatStartedActivity.hasExtra(getString(R.string.recipe_key))){
            recipe = intentThatStartedActivity.getParcelableExtra(this.getString(R.string.recipe_key));
        }
        setTitle(recipe.getName());

        if (savedInstanceState == null) {
            // get the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // create an instance of the ExoplayerFragment and add a Bundle with the
            //  RecipeStep
            ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
            Bundle recipeIntroBundle = new Bundle();
            recipeIntroBundle.putParcelable(getString(R.string.single_step_key),
                    recipe.getSteps().get(0));
            recipeIntroBundle.putBoolean("isTablet", mTwoPane);
            exoPlayerFragment.setArguments(recipeIntroBundle);

            // create an instance of the RecipeIngredientsFragment and add a Bundle with the
            //  ingredients
            RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
            Bundle ingredientsBundle = new Bundle();
            ingredientsBundle.putParcelableArrayList(getString(R.string.ingredients_key),
                    recipe.getIngredients());
            ingredientsFragment.setArguments(ingredientsBundle);

            // create an instance of the RecipeStepsFragment and add a Bundle with the steps
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            Bundle stepsBundle = new Bundle();
            stepsBundle.putParcelableArrayList(getString(R.string.steps_key), recipe.getSteps());
            recipeStepsFragment.setArguments(stepsBundle);

            if (mTwoPane){
                fragmentManager.beginTransaction()
                        .add(R.id.activityRecipeDetail_detailStepFramelayout, exoPlayerFragment)
                        .add(R.id.activityRecipeDetail_ingredientsFramelayout, ingredientsFragment)
                        .add(R.id.activityRecipeDetail_stepsFramelayout, recipeStepsFragment)
                        .commit();
                recipeStepDescriptionTv.setText(recipe.getSteps().get(0).getFullDescription());
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.activityRecipeDetail_recipeIntroFramelayout, exoPlayerFragment)
                        .add(R.id.activityRecipeDetail_ingredientsFramelayout, ingredientsFragment)
                        .add(R.id.activityRecipeDetail_stepsFramelayout, recipeStepsFragment)
                        .commit();
            }

        } else {
            recipeStepDescriptionTv.setText(savedInstanceState.getString("recipeStep"));
        }
    }

    @Override
    public void onRecipeStepClickListener(int position) {
        if (mTwoPane){
            // create an new instance of the ExoplayerFragment and add a Bundle with the
            //  RecipeStep
            ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
            Bundle recipeIntroBundle = new Bundle();
            recipeIntroBundle.putParcelable(getString(R.string.single_step_key),
                    recipe.getSteps().get(position));
            recipeIntroBundle.putBoolean("isTablet", mTwoPane);
            exoPlayerFragment.setArguments(recipeIntroBundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activityRecipeDetail_detailStepFramelayout, exoPlayerFragment)
                    .commit();
            recipeStepDescriptionTv.setText(recipe.getSteps().get(position).getFullDescription());

        } else {
            Intent intentToStepActivity = new Intent(this, RecipeStepDetailActivity.class);
            intentToStepActivity.putExtra(getString(R.string.recipe_key), recipe);
            intentToStepActivity.putExtra(getString(R.string.position_key), position);
            startActivity(intentToStepActivity);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("recipeStep", recipeStepDescriptionTv.getText().toString());
    }
}
