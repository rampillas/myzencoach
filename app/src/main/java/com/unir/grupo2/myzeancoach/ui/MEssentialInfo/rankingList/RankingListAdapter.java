package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by Cesar on 23/02/2017.
 */

public class RankingListAdapter extends RecyclerView.Adapter<RankingItemViewHolder> {

    Context context;
    List<RankingItem> rankingItemList;
    RankingListAdapter thisAdapter = this;

    public RankingListAdapter(Context context, List<RankingItem> rankingItemList) {
        this.context = context;
        this.rankingItemList = rankingItemList;
    }

    @Override
    public RankingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item_card_layout, parent, false);
        return new RankingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingItemViewHolder rankingItemViewHolder, final int position) {
        final RankingItem rankingItem = rankingItemList.get(position);
        rankingItemViewHolder.nickTextView.setText(rankingItem.getNick());
        rankingItemViewHolder.scoreTextView.setText(String.format(context.getString(R.string.score_info), rankingItem.getScore()));
    }

    @Override
    public int getItemCount() {
        return rankingItemList.size();
    }
}
