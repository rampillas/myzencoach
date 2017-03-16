package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 28/02/2017.
 */

public class QuestionTestCompletedDialog extends DialogFragment {

    @BindView(R.id.ok_button) Button okButton;
    @BindView(R.id.score_ratingBar) RatingBar scoreRatingBar;

    public interface OnStopLister {
        public void onStopDialog();
    }

    private QuestionTestCompletedDialog.OnStopLister listener;

    // Empty constructor required for DialogFragment
    public QuestionTestCompletedDialog() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStopLister) {
            listener = (OnStopLister) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement QuestionTestCompletedDialog.OnStopLister");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questions_test_completed_dialog_layout, container);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        int rate = bundle.getInt("RATE");

        scoreRatingBar.setRating(rate);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int rate = bundle.getInt("RATE");
            scoreRatingBar.setRating(rate);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        listener.onStopDialog();
    }

    @OnClick(R.id.ok_button)
    public void submit() {
        this.dismiss();
    }
}
