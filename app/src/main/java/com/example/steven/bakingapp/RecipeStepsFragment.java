package com.example.steven.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Adapters.StepsAdapter;
import com.example.steven.bakingapp.Objects.Ingredient;
import com.example.steven.bakingapp.Objects.RecipeStep;

import java.util.ArrayList;

/**
 * Created by Steven on 29/04/2018.
 */

public class RecipeStepsFragment extends Fragment implements StepsAdapter.ListItemClickListener{

    // holds the steps for the selected ingredient
    ArrayList<RecipeStep> recipeSteps = new ArrayList<>();


    OnListItemClickListener mCallback;

    /**
     * Mandatory empty constructor for the fragment
     */
    public RecipeStepsFragment() {
    }


    public interface OnListItemClickListener {
        void onRecipeStepClickListener(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnListItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the View from the layout file
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        // get the steps ArrayList for the selected recipe that was passed when the fragment was created
        if (getArguments() != null) {
            recipeSteps = getArguments().getParcelableArrayList(this.getString(R.string.steps_key));
        }

        // set up the RecyclerView for displaying the steps of the recipe
        RecyclerView recyclerView = rootView.findViewById(R.id.fragmentRecipeSteps_stepsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        StepsAdapter adapter = new StepsAdapter(recipeSteps, this, getContext());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onListItemClick(int position) {
        mCallback.onRecipeStepClickListener(position);
    }


}
