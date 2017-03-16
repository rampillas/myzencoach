package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 11/03/2017.
 */

public class DilemmaPostItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_textView) TextView dateTextView;
    @BindView(R.id.nick_textView) TextView nickTextView;
    @BindView(R.id.title_textView) TextView titleTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;
    @BindView(R.id.state_textView) TextView stateTextView;

    public DilemmaPostItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final DilemmaPost dilemmaPostItem, final DilemmaPostListAdapter.OnDilemmaPostClickListener listener) {

        dateTextView.setText(dilemmaPostItem.getDate());
        nickTextView.setText(dilemmaPostItem.getNick());
        titleTextView.setText(dilemmaPostItem.getTitle());
        descriptionTextView.setText(dilemmaPostItem.getDescription());
        stateTextView.setText(dilemmaPostItem.getState());

        /*
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemPostClick(postItem);
            }
        });*/
    }
}
