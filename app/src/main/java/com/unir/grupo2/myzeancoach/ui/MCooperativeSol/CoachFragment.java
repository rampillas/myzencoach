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
            setListView();
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

    private void setListView(){
        dilemmaPostListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        dilemmaPostListRecyclerView.setLayoutManager(linearLayoutManager);
        coachDilemmaListAdapter = new CoachListAdapter(getContext(),coachDilemmaItemList, this);
        dilemmaPostListRecyclerView.setAdapter(coachDilemmaListAdapter);
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
                    if (coachDilemmaItemList == null || coachDilemmaItemList.isEmpty()){
                        if (coachDilemmaItemList == null){
                            coachDilemmaItemList = new ArrayList<>();
                        }
                        coachDilemmaItemList.add(dilemmaNew);
                        setListView();
                        showContent();
                    }else{
                        coachDilemmaItemList.add(dilemmaNew);
                        coachDilemmaListAdapter.notifyDataSetChanged();
                    }
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
}

