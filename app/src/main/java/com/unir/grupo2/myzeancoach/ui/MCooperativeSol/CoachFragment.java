package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.content.Context;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 22/02/2017.
 */

public class CoachFragment extends Fragment implements CoachListAdapter.OnDilemmaCoachClickListener{

    @BindView(R.id.sol_coop_coach_recycler_view) RecyclerView dilemmaPostListRecyclerView;
    @BindView(R.id.no_dilemma_layout) LinearLayout noDilemmaLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;
    @BindView(R.id.floating_action_button) FloatingActionButton floatingActionButton;

    List<Dilemma> coachDilemmaItemList;
    CoachListAdapter coachDilemmaListAdapter;

    CoachFragment.OnDilemmaCoachListener dilemmaCoachListener;

    public interface OnDilemmaCoachListener{
        void onDilemmaCoachSelected(Dilemma dilemmaPost);
        void onAddDilemmaCoachButton();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof CoachFragment.OnDilemmaCoachListener){
            dilemmaCoachListener = (CoachFragment.OnDilemmaCoachListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements CoachFragment.OnDilemmaCoachPostListener");
        }
    }

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
        dilemmaCoachListener.onAddDilemmaCoachButton();
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
    public void onItemCilemmaCoachClick(Dilemma dilemma) {
        dilemmaCoachListener.onDilemmaCoachSelected(dilemma);
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

