package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 23/02/2017.
 */

public class RankingItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.winner_imageView) ImageView winnerView;
    @BindView(R.id.ranking_item_card) CardView cardView;
    @BindView(R.id.position_imageView) ImageButton positionImageButton;
    @BindView(R.id.nick_textView) TextView nickTextView;
    @BindView(R.id.score_textView) TextView scoreTextView;

    public RankingItemViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
