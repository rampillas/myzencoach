package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Question;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList.QuestionListAdapter;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList.QuestionTestCompletedDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements QuestionListAdapter.OnButtonClickListener,
        QuestionTestCompletedDialog.OnStopLister{

    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    private List<Question> questionItemList;
    private QuestionListAdapter questionListAdapter;
    private Test test;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);

        //Test test = getIntent().getExtras().getParcelable("TEST");

        Intent intent = getIntent();
        test = (Test) intent.getParcelableExtra("TEST");

        questionItemList = test.getQuestions();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        questionListAdapter = new QuestionListAdapter(this, questionItemList, test.getDescription(), this);
        recyclerView.setAdapter(questionListAdapter);

        showContent();
    }

    @Override
    public void onButtonClick(int testRate) {
        score = testRate;
        showLoading();
       /* new UpdateTestUseCase("application/json",  "Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK", test.getDescription(), score)
                .execute(new UpdateTestSubscriber());*/
    }

    @Override
    public void onStopDialog() {
        finish();
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

    private void showScoreDialog(){
        // close existing dialog fragments
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_dialog");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        QuestionTestCompletedDialog questionTestCompletedDialog = new QuestionTestCompletedDialog();
        Bundle args = new Bundle();
        args.putInt("RATE", score);
        questionTestCompletedDialog.setArguments(args);
        questionTestCompletedDialog.show(manager, "fragment_dialog");
    }

    /*private final class UpdateTestSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override public void onError(Throwable e) {
            showError();
        }

        @Override
        public void onNext(Void aVoid) {
            Toast.makeText(getBaseContext(), "asdasd", Toast.LENGTH_LONG).show();
        }


    }*/


}
