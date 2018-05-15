package com.example.steven.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.Objects.RecipeStep;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private int stepPosition;
    private TextView stepDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        stepDescriptionTv = findViewById(R.id.recipeStep_stepInfoTextView);

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity == null) finish();
        // get a reference to the Recipe object passed in the intent and set the activity name
        // with it
        if (intentThatStartedActivity.hasExtra(getString(R.string.recipe_key))) {
            recipe = intentThatStartedActivity.getParcelableExtra(getString(R.string.recipe_key));
            setTitle(recipe.getName());
        }

        // get a reference to the step position passed in the intent
        if (intentThatStartedActivity.hasExtra(getString(R.string.position_key))){
            stepPosition = intentThatStartedActivity.getIntExtra(getString(R.string.position_key), 0);
        }

        if (savedInstanceState == null) {
            RecipeStep recipeStep = recipe.getSteps().get(stepPosition);
            stepDescriptionTv.setText(recipeStep.getFullDescription());

            if (recipeStep.hasValidVideoOrImage()){
                ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(getString(R.string.single_step_key), recipeStep);
                exoPlayerFragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .add(R.id.recipeStep_frameLayout, exoPlayerFragment)
                        .commit();
            } else {
                findViewById(R.id.recipeStep_frameLayout).setVisibility(View.GONE);
            }

        } else {
            stepDescriptionTv.setText(savedInstanceState.getString("stepInfo"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("stepInfo", stepDescriptionTv.getText().toString());
    }
}
