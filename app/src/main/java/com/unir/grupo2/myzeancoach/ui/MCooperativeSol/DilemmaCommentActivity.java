package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList.DilemmaCommentListAdapter;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaComment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPost;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 17/03/2017.
 */

public class DilemmaCommentActivity extends AppCompatActivity implements DilemmaCommentListAdapter.OnDilemmaCommentClickListener{

    @BindView(R.id.dilemma_comment_recycler_view) RecyclerView recyclerView;
    private List<DilemmaComment> commentItemList;
    private DilemmaCommentListAdapter dilemmaCommentListAdapter;

    @BindView(R.id.floating_action_button) FloatingActionButton addCommentfloatingActionButton;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_comment_layout);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        DilemmaPost dilemma = (DilemmaPost) intent.getParcelableExtra("DILEMMA");

        commentItemList = dilemma.getComments();

        if (commentItemList != null && commentItemList.size() >0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            dilemmaCommentListAdapter = new DilemmaCommentListAdapter(this, dilemma, this);
            recyclerView.setAdapter(dilemmaCommentListAdapter);
        }

        if (dilemma.getState().equals("completed")){
            addCommentfloatingActionButton.setVisibility(View.GONE);
        }

        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);

    }

    @Override
    public void onXClick() {
        Toast.makeText(this,"click", Toast.LENGTH_LONG)
.show();    }
}
