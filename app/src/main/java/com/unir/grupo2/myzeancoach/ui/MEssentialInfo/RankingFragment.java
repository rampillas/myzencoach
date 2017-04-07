package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Ranking;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList.RankingListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RankingFragment extends Fragment {

    @BindView(R.id.ranking_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.no_ranking_layout) LinearLayout noRankingLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.ranking_layout,null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        //List of score elements where we save our rankings
        List<Ranking> rankingItemList = bundle.getParcelableArrayList("RANKING");

        if (rankingItemList != null && !rankingItemList.isEmpty()){

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            RankingListAdapter rankingListAdapter = new RankingListAdapter(getContext(),rankingItemList);
            recyclerView.setAdapter(rankingListAdapter);

            showContent();
        }else{
            showNoDilemma();
        }

        return view;
    }

    public void showNoDilemma() {
        noRankingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        messageNoDielmmaTextView.setText(getString(R.string.message_no_ranking));
    }

    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        noRankingLayout.setVisibility(View.GONE);
    }
}
