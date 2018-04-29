package com.example.steven.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steven.bakingapp.Adapters.StepsAdapter;
import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Steven on 29/04/2018.
 */

public class RecipeDetailFragment extends Fragment {

    ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeSteps = getArguments().getParcelableArrayList("steps");
        }
    }

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes_and_steps, container, false);

        RecyclerView rv = rootView.findViewById(R.id.recipesAndSteps_stepsRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        StepsAdapter adapter = new StepsAdapter(recipeSteps.size());
        rv.setAdapter(adapter);

        return rootView;
    }
}
