package com.example.steven.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Steven on 14/05/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailTest {

    @Rule
    public ActivityTestRule<RecipesOverviewActivity> activityTestRuleRecipeOverview =
            new ActivityTestRule<>(RecipesOverviewActivity.class);

    /**
     * Tests the Detail activity: when a step is clicked, the step detail activity opens and the
     * TextView containing info about the step is shown.
     */
    @Test
    public void onStepClicked_opensRecipeStepDetailActivity(){
        onView(withId(R.id.activityRecipes_recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fragmentRecipeSteps_stepsRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipeStep_stepInfoTextView)).check(matches(isDisplayed()));
    }


}
