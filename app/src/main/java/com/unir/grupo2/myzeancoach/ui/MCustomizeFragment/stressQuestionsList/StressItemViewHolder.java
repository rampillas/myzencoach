package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.stressQuestionsList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.QuestionsStress;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andres on 16/03/2017.
 */

public class StressItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.videoName_textView)
    TextView videoName;
    @BindView(R.id.isCompleted_textView) TextView isCOmpleted;
    @BindView(R.id.score_ratingBar)
    RatingBar scoreRatingBar;

    public StressItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final QuestionsStress questionsStress, final StressListAdapter.OnItemClickListener listener) {

        videoName.setText(questionsStress.getDescription());
        if (questionsStress.getIsCompleted()){
            isCOmpleted.setTextColor(itemView.getContext().getResources().getColor(R.color.greenApp));
            isCOmpleted.setText(R.string.completed);
            scoreRatingBar.setVisibility(View.VISIBLE);
        }else{
            isCOmpleted.setText(R.string.uncompleted);
        }
        scoreRatingBar.setRating(questionsStress.getScore());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(questionsStress);
            }
        });
    }
}
