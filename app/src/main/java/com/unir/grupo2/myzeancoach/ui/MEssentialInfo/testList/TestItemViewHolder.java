package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 24/02/2017.
 */

public class TestItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.videoName_textView) TextView videoName;
    @BindView(R.id.isCompleted_textView) TextView isCOmpleted;
    @BindView(R.id.score_ratingBar) RatingBar scoreRatingBar;

    public TestItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final Test testItem, final TesttListAdapter.OnItemClickListener listener) {

        videoName.setText(testItem.getDescription());
        if (testItem.getIsCompleted()){
            isCOmpleted.setTextColor(itemView.getContext().getResources().getColor(R.color.greenApp));
            isCOmpleted.setText(R.string.completed);
            scoreRatingBar.setRating(testItem.getScore());
            scoreRatingBar.setVisibility(View.VISIBLE);
        }else{
            isCOmpleted.setText(R.string.uncompleted);
            isCOmpleted.setTextColor(itemView.getContext().getResources().getColor(R.color.redApp));
            scoreRatingBar.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(testItem);
            }
        });

        videoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(testItem);
            }
        });

        isCOmpleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(testItem);
            }
        });
    }
}
