package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */

public class QuestionFooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.send_to_rate_button) Button videoNameButton;

    public QuestionFooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
