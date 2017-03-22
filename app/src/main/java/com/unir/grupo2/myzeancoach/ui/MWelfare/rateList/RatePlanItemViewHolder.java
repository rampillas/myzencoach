package com.unir.grupo2.myzeancoach.ui.MWelfare.rateList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */

public class RatePlanItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.question_textView) TextView questionTextView;
    @BindView(R.id.answer_editText) EditText answerEditText;

    public RatePlanItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
