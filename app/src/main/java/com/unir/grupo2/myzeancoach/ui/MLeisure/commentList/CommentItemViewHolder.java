package com.unir.grupo2.myzeancoach.ui.MLeisure.commentList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 12/03/2017.
 */

public class CommentItemViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.date_comment_textView) TextView dateTextView;
    @BindView(R.id.description_comment_textView) TextView descriptionTextView;
    @BindView(R.id.line_view) View lineView;

    public CommentItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
