package com.example.steven.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.R;

/**
 * Created by Steven on 30/04/2018.
 */

public class RecipeStepFragment extends Fragment {

    private RecipeStep recipeStep;

    public RecipeStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        if (getArguments() != null) {
            recipeStep = getArguments().getParcelable(getString(R.string.single_step_key));
        }

        TextView stepDescription = rootView.findViewById(R.id.recipeSteps_stepDescription);
        stepDescription.append(recipeStep.getFullDescription());
        return rootView;

    }
}
