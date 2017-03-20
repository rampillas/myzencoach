package com.unir.grupo2.myzeancoach.ui.MWelfare.planList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;

import java.util.List;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseItemViewHolder> {

    Context context;
    List<ExerciseWelfare> exerciseItemList;
    ExerciseListAdapter exerciseAdapter = this;

    public interface OnItemExerciseClickListener{
        public void onItemExerciseClick(ExerciseWelfare exercise);
    }

    ExerciseListAdapter.OnItemExerciseClickListener listener;

    public ExerciseListAdapter(Context context, List<ExerciseWelfare> exerciseItemList, ExerciseListAdapter.OnItemExerciseClickListener listener) {
        this.context = context;
        this.exerciseItemList = exerciseItemList;
        this.listener = listener;
    }

    @Override
    public ExerciseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_current_plan_item_card, parent, false);
        return new ExerciseItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseItemViewHolder exerciseItemViewHolder, final int position) {
        final ExerciseWelfare exercise = exerciseItemList.get(position);
        exerciseItemViewHolder.bind(exercise, listener, context);
    }

    @Override
    public int getItemCount() {
        return exerciseItemList.size();
    }
}
