package com.unir.grupo2.myzeancoach.ui.MWelfare.planList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class ExerciseItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.week_number_textView) TextView weekNumberTextView;
    @BindView(R.id.title_textView) TextView titleTextView;

    public ExerciseItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final ExerciseWelfare exercise,
                     final ExerciseListAdapter.OnItemExerciseClickListener listener, final Context context) {

        weekNumberTextView.setText(context.getString(R.string.week_number,exercise.getWeek()));
        titleTextView.setText(exercise.getDescription());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemExerciseClick(exercise);
            }
        });
    }

}
