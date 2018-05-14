package com.example.steven.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Created by Steven on 9/05/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeOverviewTest {

    private String ingredientsHeader = "Ingredients";
    private String stepsHeader = "Steps";


    @Rule public ActivityTestRule<RecipesOverviewActivity> mActivityTestRule =
            new ActivityTestRule<RecipesOverviewActivity>(RecipesOverviewActivity.class);

    /**
     * Tests the overview activity: when a list item is selected, the detail activity should open
     * and the TextViews for the Ingredients and Steps should be displayed
     */
    @Test
    public void onRecipeClick_opensRecipeDetails(){
        onView(withId(R.id.activityRecipes_recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.activityRecipes_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(ingredientsHeader)).check(matches(isDisplayed()));
        onView(withText(stepsHeader)).check(matches(isDisplayed()));
    }

}
