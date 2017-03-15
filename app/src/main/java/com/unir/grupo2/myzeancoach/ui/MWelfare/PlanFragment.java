package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MWelfare.planList.PlanItem;
import com.unir.grupo2.myzeancoach.ui.MWelfare.planList.PlanListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */


public class PlanFragment extends Fragment {

    //List of score elements where we save our rankings
    List<PlanItem> planItemList;

    //It will receive the taskItemList and it will be passed to the recyclerView
    PlanListAdapter planListAdapter;

    //It will receive the adapter
    @BindView(R.id.plan_recycler_view) RecyclerView recyclerView;

    //LayoutManager reference
    LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_layout, null);
        ButterKnife.bind(this, view);
        planItemList = new ArrayList<>();

        PlanItem planItem1 = new PlanItem ("Para la ", 1);
        PlanItem planItem2 = new PlanItem ("Para la ", 2);
        PlanItem planItem3 = new PlanItem ("Para la ", 3);
        PlanItem planItem4 = new PlanItem ("Para la ", 4);
        PlanItem planItem5 = new PlanItem ("Para la ", 5);
        PlanItem planItem6 = new PlanItem ("Para la ", 6);
        PlanItem planItem7 = new PlanItem ("Para la ", 7);
        PlanItem planItem8 = new PlanItem ("Para la ", 8);

        planItemList.add(planItem1);
        planItemList.add(planItem2);
        planItemList.add(planItem3);
        planItemList.add(planItem4);
        planItemList.add(planItem5);
        planItemList.add(planItem6);
        planItemList.add(planItem7);
        planItemList.add(planItem8);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        planListAdapter = new PlanListAdapter(getContext(), planItemList);
        recyclerView.setAdapter(planListAdapter);

        return view;

    }


}

