package com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by andres on 16/03/2017.
 */

public class StressItemViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.lastCard)
    LinearLayout lastCard;
    @Nullable
    @BindView(R.id.normalCard)
    LinearLayout normalCard;
    @Nullable
    @BindView(R.id.question_textView)
    TextView questionTitle;
    @Nullable
    @BindView(R.id.answer1_radio)
    RadioButton answer1_radio;
    @Nullable
    @BindView(R.id.answer2_radio)
    RadioButton answer2_radio;
    @Nullable
    @BindView(R.id.answer3_radio)
    RadioButton answer3_radio;
    @Nullable
    @BindView(R.id.answer4_radio)
    RadioButton answer4_radio;
    @Nullable
    @BindView(R.id.button)
    Button enviar;
    @BindView(R.id.buttonFinal)
    Button enviarFinal;
    @Nullable
    @BindView(R.id.answer_radioGroup)
    RadioGroup radioGroup;
    @BindColor(R.color.redStress)
    int red;
    @BindColor(R.color.blueStress)
    int blue;
    @BindColor(R.color.yellowStress)
    int yellow;
    @BindColor(R.color.orangeStress)
    int orange;
    @BindColor(R.color.black)
    int black;
    StressListAdapter.OnItemClickListener listener = null;
    String answer = "";
    StressQuestionObject stressQuestionObject;
    int selected = -1;

    @Optional
    @OnClick(R.id.buttonFinal)
    public void finalButtonPressed(){
        listener.OnFinalButtonSelected();
    }

    @Optional
    @OnClick(R.id.button)
    public void enviarRespuesta() {
        int selected_radio_button = radioGroup.getCheckedRadioButtonId();
        // This will get the radiobutton in the radiogroup that is checked
        RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        if (selected_radio_button == -1) {
            answer = "noitemselected";
        } else {
            answer = checkedRadioButton.getText().toString();
        }
        listener.onSendClick(answer, stressQuestionObject);
    }

    public StressItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final StressQuestionObject questionsStress, final StressListAdapter.OnItemClickListener listener) {
        this.stressQuestionObject = questionsStress;
        this.listener = listener;
        //separamos la ultima tarjeta
        if (!questionsStress.getDescription().equalsIgnoreCase("ENDELEMENT")) {
            normalCard.setVisibility(View.VISIBLE);
            lastCard.setVisibility(View.GONE);
            questionTitle.setText(questionsStress.getDescription());
            answer1_radio.setText(questionsStress.getElementos().get(0).getDescription());
            answer1_radio.setTextColor(getColor(questionsStress.getElementos().get(0).getColor()));
            answer2_radio.setText(questionsStress.getElementos().get(1).getDescription());
            answer2_radio.setTextColor(getColor(questionsStress.getElementos().get(1).getColor()));
            answer3_radio.setText(questionsStress.getElementos().get(2).getDescription());
            answer3_radio.setTextColor(getColor(questionsStress.getElementos().get(2).getColor()));
            answer4_radio.setText(questionsStress.getElementos().get(3).getDescription());
            answer4_radio.setTextColor(getColor(questionsStress.getElementos().get(3).getColor()));
        }else {
            normalCard.setVisibility(View.GONE);
            lastCard.setVisibility(View.VISIBLE);
        }

    }

    public int getColor(String color) {
        switch (color) {
            case "azul":
                return blue;
            case "amarillo":
                return yellow;
            case "naranja":
                return orange;
            case "rojo":
                return red;
            default:
                return black;
        }
    }
}
