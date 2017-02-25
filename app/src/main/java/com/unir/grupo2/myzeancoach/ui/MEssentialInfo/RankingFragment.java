package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList.RankingItem;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList.RankingListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RankingFragment extends Fragment {


    //List of score elements where we save our rankings
    List<RankingItem> rankingItemList;

    //It will receive the taskItemList and it will be passed to the recyclerView
    RankingListAdapter rankingListAdapter;

    //It will receive the adapter
    @BindView(R.id.ranking_recycler_view) RecyclerView recyclerView;

     //LayoutManager reference
    LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.ranking_layout,null);
        ButterKnife.bind(this, view);
        rankingItemList = new ArrayList<>();

        RankingItem rankingItem1 = new RankingItem("Ceokin", 300);
        RankingItem rankingItem2 = new RankingItem("Carles", 220);
        RankingItem rankingItem3 = new RankingItem("Merlu", 200);
        RankingItem rankingItem4 = new RankingItem("Armin", 100);
        RankingItem rankingItem5 = new RankingItem("Merlu", 89);
        RankingItem rankingItem6 = new RankingItem("Day", 22);

        rankingItemList.add(rankingItem1);
        rankingItemList.add(rankingItem2);
        rankingItemList.add(rankingItem3);
        rankingItemList.add(rankingItem4);
        rankingItemList.add(rankingItem5);
        rankingItemList.add(rankingItem6);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        rankingListAdapter = new RankingListAdapter(getContext(), rankingItemList);
        recyclerView.setAdapter(rankingListAdapter);

        return view;
    }

}
