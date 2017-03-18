package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.stressQuestionsList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.QuestionsStress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andres on 16/03/2017.
 */

public class StressItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.question_textView)
    TextView questionTitle;
    @BindView(R.id.answer1_radio)
    RadioButton answer1_radio;
    @BindView(R.id.answer2_radio)
    RadioButton answer2_radio;
    @BindView(R.id.answer3_radio)
    RadioButton answer3_radio;
    @BindView(R.id.answer4_radio)
    RadioButton answer4_radio;

    public StressItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final QuestionsStress questionsStress, final StressListAdapter.OnItemClickListener listener) {
        questionTitle.setText("questiosStress.getQuestion();");
        answer1_radio.setText("questionsStress.getAnswer1()");
        answer2_radio.setText("questionsStress.getAnswer2()");
        answer3_radio.setText("questionsStress.getAnswer3()");
        answer4_radio.setText("questionsStress.getAnswer4()");


    }
}
