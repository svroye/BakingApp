package com.example.steven.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.steven.bakingapp.Fragments.RecipeStepFragment;
import com.example.steven.bakingapp.Objects.RecipeStep;

public class RecipeStepActivity extends AppCompatActivity {

    private RecipeStep recipeStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity == null) finish();
        if (intentThatStartedActivity.hasExtra(getString(R.string.single_step_key))) {
            recipeStep = intentThatStartedActivity.getParcelableExtra(getString(R.string.single_step_key));
        }

        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.single_step_key), recipeStep);
        fragment.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .add(R.id.recipeStep_frameLayout, fragment)
                .commit();
    }


}
