package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */


public class QuestionHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.videoName_textView) TextView videoName;

    public QuestionHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
