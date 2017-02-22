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
import com.unir.grupo2.myzeancoach.ui.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RankingFragment extends Fragment {


    //List of score elements where we save our rankings
    List<Score> rankingItemList;

    //It will receive the taskItemList and it will be passed to the recyclerView
   // RankingListAdapter rankingListAdapter;

    //It will receive the adapter
    RecyclerView recyclerView;

     //LayoutManager reference
    LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rankingItemList = new ArrayList<>();

        return inflater.inflate(R.layout.ranking_layout,null);
    }

}
