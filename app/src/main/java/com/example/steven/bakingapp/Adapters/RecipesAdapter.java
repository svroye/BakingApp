package com.example.steven.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.Recipe;
import com.example.steven.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 29/04/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    ArrayList<Recipe> recipesList;
    ListItemClickListener listener;

    public interface ListItemClickListener {
        void onListItemClick(int position);
    }

    public RecipesAdapter(ArrayList<Recipe> recipes, ListItemClickListener listener) {
        this.recipesList = recipes;
        this.listener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipesList.get(position);
        holder.recipeNameTv.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (null == recipesList) return 0;
        return recipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeNameTv;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameTv = itemView.findViewById(R.id.recipeListItem_recipeNameTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }
    }
}
