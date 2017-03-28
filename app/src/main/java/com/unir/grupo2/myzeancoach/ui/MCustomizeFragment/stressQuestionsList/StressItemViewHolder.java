package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.stressQuestionsList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.button)
    Button enviar;
    @BindView(R.id.answer_radioGroup)
    RadioGroup radioGroup;
    StressListAdapter.OnItemClickListener listener = null;
    String answer = "";
    StressQuestionObject stressQuestionObject;

    @OnClick(R.id.button)
    public void enviarRespuesta() {
        int selected_radio_button = radioGroup.getCheckedRadioButtonId();
        if (selected_radio_button == -1) {
            answer = "noitemselected";
        } else {
            answer = stressQuestionObject.getElementos().get(selected_radio_button).getDescription();

        }
        listener.onSendClick(answer, stressQuestionObject);
    }

    public StressItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final StressQuestionObject questionsStress, final StressListAdapter.OnItemClickListener listener) {
        this.stressQuestionObject = questionsStress;
        this.listener=listener;
        questionTitle.setText(questionsStress.getDescription());
        answer1_radio.setText(questionsStress.getElementos().get(0).getDescription());
        answer2_radio.setText(questionsStress.getElementos().get(1).getDescription());
        answer3_radio.setText(questionsStress.getElementos().get(2).getDescription());
        answer4_radio.setText(questionsStress.getElementos().get(3).getDescription());


    }
}
