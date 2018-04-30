package com.example.steven.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.Objects.RecipeStep;
import com.example.steven.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Steven on 29/04/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private ArrayList<RecipeStep> steps;
    public ListItemClickListener listener;
    private Context context;

    public interface ListItemClickListener {
        void onListItemClick(int position);
    }

    public StepsAdapter(ArrayList<RecipeStep> steps, ListItemClickListener listener, Context context) {
        this.steps = steps;
        this.listener = listener;
        this.context = context;
    }


    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item,
                parent, false);
        StepsViewHolder viewHolder = new StepsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        RecipeStep currentStep = steps.get(position);
        holder.stepNumberTv.setText(context.getString(R.string.single_step_item, position,
                currentStep.getShortDescription()));
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView stepNumberTv;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepNumberTv = itemView.findViewById(R.id.stepsListItem_stepTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }
    }
}
