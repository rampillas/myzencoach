package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 11/03/2017.
 */

public class PostItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title_textView) TextView titleTextView;
    @BindView(R.id.category_textView) TextView categoryTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;
    @BindView(R.id.like_number_textView) TextView likeNumberTextView;
    @BindView(R.id.comments_number_textView) TextView commentNumberTextView;
    @BindView(R.id.like_button) Button likeButton;
    @BindView(R.id.comment_button) Button commentButton;


    public PostItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final PostItem postItem, final PostListAdapter.OnItemPostClickListener listener) {

        titleTextView.setText(postItem.getTitle());
        categoryTextView.setText(postItem.getCategory());
        descriptionTextView.setText(postItem.getDescription());
        likeNumberTextView.setText(Integer.toString(postItem.getLikeNumber()));
        commentNumberTextView.setText(Integer.toString(postItem.getCommentNumber()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemPostClick(postItem);
            }
        });
    }
}
