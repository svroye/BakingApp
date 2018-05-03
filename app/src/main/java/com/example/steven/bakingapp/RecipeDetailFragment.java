package com.example.steven.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Adapters.StepsAdapter;
import com.example.steven.bakingapp.Objects.Ingredient;
import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.R;
import com.example.steven.bakingapp.RecipeStepActivity;

import java.util.ArrayList;

/**
 * Created by Steven on 29/04/2018.
 */

public class RecipeDetailFragment extends Fragment implements StepsAdapter.ListItemClickListener{

    // ArrayList holding the steps
    ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
    ArrayList<Ingredient> ingredients = new ArrayList<>();

    OnListItemClickListener mCallback;

    public RecipeDetailFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        if (getArguments() != null) {
            recipeSteps = getArguments().getParcelableArrayList(this.getString(R.string.steps_key));
            ingredients = getArguments().getParcelableArrayList(this.getString(R.string.ingredients_key));
        }

        TextView tv = rootView.findViewById(R.id.recipeDetail_ingredientsTv);
        for (int i = 0; i < ingredients.size(); i++){
            Ingredient ingredient = ingredients.get(i);
            String ingredientLine;
            if (ingredient.getQuantity() == Math.floor(ingredient.getQuantity())) {
                ingredientLine = getString(R.string.ingredient_quantity_measure_int, (int) ingredient.getQuantity(),
                        ingredient.getMeasure(), ingredient.getName());
            } else {
                ingredientLine = getString(R.string.ingredient_quantity_measure_float, ingredient.getQuantity(),
                        ingredient.getMeasure(), ingredient.getName());
            }

            tv.append(ingredientLine + "\n");
        }

        RecyclerView rv = rootView.findViewById(R.id.recipeDetail_stepsRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        StepsAdapter adapter = new StepsAdapter(recipeSteps, this, getContext());
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onListItemClick(int position) {
        mCallback.onRecipeStepClickListener(position);
    }
}
