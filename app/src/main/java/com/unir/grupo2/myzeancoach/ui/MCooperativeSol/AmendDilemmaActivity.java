package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.CommentsCoach;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.suggestionCoachList.SuggestionListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 30/03/2017.
 */

public class AmendDilemmaActivity extends AppCompatActivity{

    @BindView(R.id.coach_comments_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.state_textView) TextView stateTextView;
    @BindView(R.id.title_editText) EditText titleEditText;
    @BindView(R.id.description_editText) EditText descriptionEditText;

    private List<CommentsCoach> suggestionItemList;
    private SuggestionListAdapter suggestionListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_amend_dilemma_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Dilemma dilemma = (Dilemma) intent.getParcelableExtra("COACH_DILEMMA");
        suggestionItemList = dilemma.getCommentsCoach();

        switch (dilemma.getState()) {
            case "waitingForAnswer":
                stateTextView.setText(getString(R.string.state_waiting_for_answer));
                stateTextView.setTextColor(getColor(R.color.blueApp));
                break;
            case "refused":
                stateTextView.setText(getString(R.string.state_refused));
                stateTextView.setTextColor(getColor(R.color.redApp));
                break;
            default:
                stateTextView.setText(getString(R.string.state_waiting_for_answer));
                stateTextView.setTextColor(getColor(R.color.blueApp));
        }

        titleEditText.setText(dilemma.getTitle());
        descriptionEditText.setText(dilemma.getDescription());

        if (suggestionItemList != null && !suggestionItemList.isEmpty()){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            suggestionListAdapter = new SuggestionListAdapter(this, dilemma.getCommentsCoach());
            recyclerView.setAdapter(suggestionListAdapter);
        }
    }
}

