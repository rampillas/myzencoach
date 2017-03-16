package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Remainders;
import com.unir.grupo2.myzeancoach.domain.model.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 24/02/2017.
 */

public class RemaindersItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.videoName_textView) TextView videoName;
    @BindView(R.id.isCompleted_textView) TextView isCOmpleted;
    @BindView(R.id.score_ratingBar) RatingBar scoreRatingBar;

    public RemaindersItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final Remainders remaindersItem, final RemaindersListAdapter.OnItemClickListener listener) {

        videoName.setText(remaindersItem.getDescription());
        if (remaindersItem.getIsCompleted()){
            isCOmpleted.setTextColor(itemView.getContext().getResources().getColor(R.color.greenApp));
            isCOmpleted.setText(R.string.completed);
            scoreRatingBar.setVisibility(View.VISIBLE);
        }else{
            isCOmpleted.setText(R.string.uncompleted);
        }
        scoreRatingBar.setRating(remaindersItem.getScore());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(remaindersItem);
            }
        });
    }
}
