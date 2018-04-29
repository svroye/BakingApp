package com.example.steven.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.steven.bakingapp.Fragments.RecipeDetailFragment;
import com.example.steven.bakingapp.Objects.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity == null) finish();
        if (intentThatStartedActivity.hasExtra("recipe")){
            recipe = intentThatStartedActivity.getParcelableExtra("recipe");
        }
        setTitle(recipe.getName());

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", recipe.getSteps());
        recipeDetailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.activityDetail_ingredientsAndSteps, recipeDetailFragment)
                .commit();


    }
}
