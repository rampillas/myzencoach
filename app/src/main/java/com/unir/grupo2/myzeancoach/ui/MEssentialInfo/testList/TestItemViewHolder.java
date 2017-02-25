package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

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
}
