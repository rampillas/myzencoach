package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Comment;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList.DilemmaCommentListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 17/03/2017.
 */

public class DilemmaCommentActivity extends AppCompatActivity implements DilemmaCommentListAdapter.OnDilemmaCommentClickListener{

    private final static int DILEMMA_REQUEST = 1;

    @BindView(R.id.dilemma_comment_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.floating_action_button) FloatingActionButton addCommentfloatingActionButton;
    @BindView(R.id.no_comments_layout) LinearLayout noCommentsLayout;

    private List<Comment> commentItemList;
    private DilemmaCommentListAdapter dilemmaCommentListAdapter;
    private Dilemma dilemma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_comment_layout);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        dilemma = (Dilemma) intent.getParcelableExtra("DILEMMA");
        boolean isFromDilemma = intent.getBooleanExtra("IS_FROM_MYDILEMMA", false);

        commentItemList = dilemma.getComments();

        if (commentItemList != null && commentItemList.size() >0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            dilemmaCommentListAdapter = new DilemmaCommentListAdapter(this, dilemma, this);
            recyclerView.setAdapter(dilemmaCommentListAdapter);
            showContent();
            if (dilemma.getNickUser().equals(Utils.getUserFromPreference(this))){
                addCommentfloatingActionButton.setVisibility(View.GONE);
        }
        }else{
            showNoComments();
            addCommentfloatingActionButton.setVisibility(View.GONE);
        }

        if (dilemma.getState().equals("completed") || isFromDilemma){
            addCommentfloatingActionButton.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(DilemmaCommentActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floating_action_button)
    void addEvent(View view) {
        launchAddCommentActivity();
    }

    public void showNoComments() {
        noCommentsLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        noCommentsLayout.setVisibility(View.GONE);
    }

    @Override
    public void onXClick() {

    }

    private void launchAddCommentActivity(){
        Intent intent = new Intent(this, AddCommentActivity.class);
        intent.putExtra("DILEMMA",dilemma);
        startActivityForResult(intent, DILEMMA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == DILEMMA_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Dilemma dilemmaNew = data.getParcelableExtra("DILEMMA");
                    //coachDilemmaItemList.add(dilemmaNew);
                    //coachDilemmaListAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
