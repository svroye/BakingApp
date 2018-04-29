package com.example.steven.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.steven.bakingapp.R;

/**
 * Created by Steven on 29/04/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private int steps;

    public StepsAdapter(int steps) {
        this.steps = steps;
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
        holder.stepNumberTv.setText("STEP " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return steps;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {

        TextView stepNumberTv;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepNumberTv = itemView.findViewById(R.id.stepsListItem_stepTv);
        }
    }
}
