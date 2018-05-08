package com.example.steven.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.steven.bakingapp.Objects.Recipe;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private int stepPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity == null) finish();
        if (intentThatStartedActivity.hasExtra(getString(R.string.recipe_key))) {
            recipe = intentThatStartedActivity.getParcelableExtra(getString(R.string.recipe_key));
            setTitle(recipe.getName());
        }
        if (intentThatStartedActivity.hasExtra(getString(R.string.position_key))){
            stepPosition = intentThatStartedActivity.getIntExtra(getString(R.string.position_key), 0);
        }

        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.single_step_key), recipe.getSteps().get(stepPosition));
        fragment.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .add(R.id.recipeStep_frameLayout, fragment)
                .commit();
    }


}
