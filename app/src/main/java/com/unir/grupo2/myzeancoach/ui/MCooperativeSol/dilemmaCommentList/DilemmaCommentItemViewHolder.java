package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */

public class DilemmaCommentItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.nick_textView) TextView nickTextView;
    @BindView(R.id.date_comment_textView) TextView dateTextView;
    @BindView(R.id.description_comment_textView) TextView descriptionTextView;
    @BindView(R.id.pros_textView) TextView prosTextView;
    @BindView(R.id.cons_textView) TextView consTextView;
    @BindView(R.id.like_check_box) CheckBox likeCheckBox;
    @BindView(R.id.feedback_layout) LinearLayout feedbackLinearLayout;
    @BindView(R.id.feedbackTextView) TextView feedbackTextView;
    @BindView(R.id.feedback_editText) EditText feedbackEditText;

    public DilemmaCommentItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
