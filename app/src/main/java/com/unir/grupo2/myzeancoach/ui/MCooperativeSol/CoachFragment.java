package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.coachList.CoachListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Cesar on 22/02/2017.
 */

public class CoachFragment extends Fragment implements CoachListAdapter.OnDilemmaCoachClickListener{

    private final static int DILEMMA_NEW_REQUEST = 1;
    private final static int DILEMMA_UPDATE_REQUEST = 2;

    @BindView(R.id.sol_coop_coach_recycler_view) RecyclerView dilemmaPostListRecyclerView;
    @BindView(R.id.no_dilemma_layout) LinearLayout noDilemmaLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;
    @BindView(R.id.floating_action_button) FloatingActionButton floatingActionButton;

    ArrayList<Dilemma> coachDilemmaItemList;
    CoachListAdapter coachDilemmaListAdapter;

    int positionClicked;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coop_sol_coach_layout,null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        coachDilemmaItemList = bundle.getParcelableArrayList("COACH_DILEMMAS");

        if (coachDilemmaItemList != null && !coachDilemmaItemList.isEmpty()){
            dilemmaPostListRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            dilemmaPostListRecyclerView.setLayoutManager(linearLayoutManager);
            coachDilemmaListAdapter = new CoachListAdapter(getContext(),coachDilemmaItemList, this);
            dilemmaPostListRecyclerView.setAdapter(coachDilemmaListAdapter);
            showContent();
        }else{
            showNoDilemma();
        }

        return view;
    }

    @OnClick(R.id.floating_action_button)
    void addEvent(View view) {
        Intent intent = new Intent(getActivity(), AddDilemmaActivity.class);
        startActivityForResult(intent,DILEMMA_NEW_REQUEST);
    }

    @Override
    public void onItemCilemmaCoachClick(Dilemma dilemma, int position) {
        positionClicked = position;
        Intent intent = new Intent(getActivity(), AmendDilemmaActivity.class);
        intent.putExtra("DILEMMA_UPDATE", dilemma);
        startActivityForResult(intent,DILEMMA_UPDATE_REQUEST);
    }

    public void showNoDilemma() {
        noDilemmaLayout.setVisibility(View.VISIBLE);
        dilemmaPostListRecyclerView.setVisibility(View.GONE);
        messageNoDielmmaTextView.setText(getString(R.string.message_no_coach_dilemmas));
    }

    public void showContent() {
        dilemmaPostListRecyclerView.setVisibility(View.VISIBLE);
        noDilemmaLayout.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == DILEMMA_NEW_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Dilemma dilemmaNew = data.getParcelableExtra("DILEMMA_NEW");
                    coachDilemmaItemList.add(dilemmaNew);
                    coachDilemmaListAdapter.notifyDataSetChanged();
                }
            }
        }else if (requestCode == DILEMMA_UPDATE_REQUEST){
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Dilemma dilemmaUpdated = data.getParcelableExtra("DILEMMA_UPDATED");
                    coachDilemmaItemList.set(positionClicked, dilemmaUpdated);
                    coachDilemmaListAdapter.notifyDataSetChanged();
                }
            }
        }
    }


/*
    private void updateData() {
        showLoading();
        //we must pass a real token**
        new VideosUseCase("Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK").execute(new VideosSubscriber());
    }

    /**
     * Method used to show error view
     */
  /*  public void showError() {
        postListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
  /*  public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        postListRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
 /*   public void showContent() {
        postListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private void updateList(List<PostItem> postList){
        postItemList = postList;
        postListAdapter = new VideoListAdapter(getContext(),postItemList, this);
        postListRecyclerView.setAdapter(postListAdapter);
    }

    */

    /*private final class PostsSubscriber extends Subscriber<List<PostItem>> {
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
        public void onNext(List<PostItem> postList) {
            updateList(postList);
        }
    }*/
}

