package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class ExercisePlanFragment extends Fragment {

    @BindView(R.id.description_textView) TextView descriptionTextView;
    @BindView(R.id.instructions_textView) TextView instructionTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_exercise_layout, null);
        ButterKnife.bind(this, view);

        ExerciseWelfare exercise;
        Bundle bundle = getArguments();
        if (bundle != null) {
            exercise = bundle.getParcelable("EXERCISE");
        }else{
            exercise = null;
        }

        descriptionTextView.setText(exercise.getDescription());
        instructionTextView.setText(exercise.getInstructions());

        return view;
    }
}
