package com.example.steven.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.Objects.RecipeStep;
import com.google.gson.Gson;

/**
 * Activity showing the details of recipe. This includes the Introductory video, ingredients and steps
 * For tablet, this also holds the navigation between steps by using the Master Detail layout structure.
 */
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
        if (findViewById(R.id.activityRecipeDetail_stepInfoTextView) != null) {
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

            RecipeStep introStep = recipe.getSteps().get(0);
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
                // tablet view
                recipeStepDescriptionTv.setText(introStep.getFullDescription());
            }

            // create an instance of the ExoplayerFragment and add a Bundle with the
            //  RecipeStep
            if (introStep.hasValidVideoOrImage()) {
                ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
                Bundle recipeIntroBundle = new Bundle();
                recipeIntroBundle.putParcelable(getString(R.string.single_step_key), introStep);
                recipeIntroBundle.putBoolean("isTablet", mTwoPane);
                exoPlayerFragment.setArguments(recipeIntroBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.activityRecipeDetail_ingredientsFramelayout, ingredientsFragment)
                        .add(R.id.activityRecipeDetail_stepsFramelayout, recipeStepsFragment)
                        .add(R.id.activityRecipeDetail_detailStepFramelayout, exoPlayerFragment)
                        .commit();

            } else {
                findViewById(R.id.activityRecipeDetail_detailStepFramelayout).setVisibility(View.GONE);
                fragmentManager.beginTransaction()
                        .add(R.id.activityRecipeDetail_ingredientsFramelayout, ingredientsFragment)
                        .add(R.id.activityRecipeDetail_stepsFramelayout, recipeStepsFragment)
                        .commit();
            }

        } else {
            if (mTwoPane) {
                // only for the tablet the text needs to be saved; for phone, this is present on a
                // different activity
                recipeStepDescriptionTv.setText(savedInstanceState.getString("recipeStep"));
            }
        }

        // update Preferences to save the currently selected recipe and send broadcast for updating
        // the widget
        updateSharedPreferences();
        sendBroadcast();
    }

    /**
     * Send broadcast for updating widget
     */
    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
    }

    /**
     * update shared preferences file with the newly selected recipe
     */
    private void updateSharedPreferences() {
        // transform the Recipe object to a String using GSon
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("recipekey", recipeString);
        editor.commit();
    }

    @Override
    public void onRecipeStepClickListener(int position) {
        if (mTwoPane){
            // tablet view
            // create an new instance of the ExoplayerFragment and add a Bundle with the
            //   and boolean specifying tablet or phone view
            ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
            Bundle recipeIntroBundle = new Bundle();
            recipeIntroBundle.putParcelable(getString(R.string.single_step_key),
                    recipe.getSteps().get(position));
            recipeIntroBundle.putBoolean("isTablet", mTwoPane);
            exoPlayerFragment.setArguments(recipeIntroBundle);
            // replace the old fragment with the newly selected step
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activityRecipeDetail_detailStepFramelayout, exoPlayerFragment)
                    .commit();
            // set the text for the current step
            recipeStepDescriptionTv.setText(recipe.getSteps().get(position).getFullDescription());

        } else {
            // phone view
            // open the step detail activity
            // add the recipe and the position of the step which was selected
            Intent intentToStepActivity = new Intent(this, RecipeStepDetailActivity.class);
            intentToStepActivity.putExtra(getString(R.string.recipe_key), recipe);
            intentToStepActivity.putExtra(getString(R.string.position_key), position);
            startActivity(intentToStepActivity);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mTwoPane) {
            // only save the text for tablet
            outState.putString("recipeStep", recipeStepDescriptionTv.getText().toString());
        }
    }
}
