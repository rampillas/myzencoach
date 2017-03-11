package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList.InterestItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList.InterestListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class InterestsFragment extends Fragment {

    @BindView(R.id.interest_recycler_view) RecyclerView interestReciclerView;
    @BindView(R.id.save_interests_button) Button saveInterestsButton;

    List<InterestItem> interestItemList;
    InterestListAdapter interestListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interests_layout,null);
        ButterKnife.bind(this, view);

        interestItemList = new ArrayList<InterestItem>();

        InterestItem interestItem1 = new InterestItem("viajes", false);
        InterestItem interestItem2 = new InterestItem("tecnologia", true);
        InterestItem interestItem3 = new InterestItem("naturaleza", false);
        InterestItem interestItem4 = new InterestItem("deportes", false);
        InterestItem interestItem5 = new InterestItem("salud", true);
        InterestItem interestItem6 = new InterestItem("naval", false);
        InterestItem interestItem7 = new InterestItem("trabajo", true);
        InterestItem interestItem8 = new InterestItem("tiempo", false);
        InterestItem interestItem9 = new InterestItem("cultura", true);
        InterestItem interestItem10 = new InterestItem("politica", true);

        interestItemList.add(interestItem1);
        interestItemList.add(interestItem2);
        interestItemList.add(interestItem3);
        interestItemList.add(interestItem4);
        interestItemList.add(interestItem5);
        interestItemList.add(interestItem6);
        interestItemList.add(interestItem7);
        interestItemList.add(interestItem8);
        interestItemList.add(interestItem9);
        interestItemList.add(interestItem10);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        interestReciclerView.setLayoutManager(linearLayoutManager);
        interestListAdapter = new InterestListAdapter(getContext(), interestItemList);
        interestReciclerView.setAdapter(interestListAdapter);

        return view;
    }

}
