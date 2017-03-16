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

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.RankingUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Ranking;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.rankingList.RankingListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RankingFragment extends Fragment {


    //List of score elements where we save our rankings
    List<Ranking> rankingItemList;

    //It will receive the taskItemList and it will be passed to the recyclerView
    RankingListAdapter rankingListAdapter;

    //It will receive the adapter
    @BindView(R.id.ranking_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

     //LayoutManager reference
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.ranking_layout,null);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        updateData();

        return view;
    }

    private void updateData() {
        showLoading();
        //we must pass a real token**
        new RankingUseCase().execute(new RankingSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private void updateList(List<Ranking> rankingList){
        rankingItemList = rankingList;
        rankingListAdapter = new RankingListAdapter(getContext(),rankingItemList);
        recyclerView.setAdapter(rankingListAdapter);
    }

    private final class RankingSubscriber extends Subscriber<List<Ranking>> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<Ranking> rankingList) {
            updateList(rankingList);
        }
    }

}
