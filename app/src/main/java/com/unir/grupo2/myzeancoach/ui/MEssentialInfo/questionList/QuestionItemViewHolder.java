package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */

public class QuestionItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.question_number_textView) TextView questionNumberTextView;
    @BindView(R.id.question_textView) TextView questionTextView;
    @BindView(R.id.answer_radioGroup) RadioGroup answerRadioGroup;
    @BindView(R.id.answer1_radio) RadioButton answer1Radio;
    @BindView(R.id.answer2_radio) RadioButton answer2Radio;
    @BindView(R.id.answer3_radio) RadioButton answer3Radio;

    public QuestionItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
