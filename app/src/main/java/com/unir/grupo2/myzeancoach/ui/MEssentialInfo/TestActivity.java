package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.UpdateTestUseCase;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.domain.model.Question;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList.QuestionListAdapter;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList.QuestionTestCompletedDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Utils.isNewConnection;

public class TestActivity extends AppCompatActivity implements QuestionListAdapter.OnButtonClickListener,
        QuestionTestCompletedDialog.OnStopLister{

    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    private List<Question> questionItemList;
    private QuestionListAdapter questionListAdapter;
    private Test test;
    private int score;
    private boolean isUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        test = (Test) intent.getParcelableExtra("TEST");

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        questionItemList = test.getQuestions();
        questionListAdapter = new QuestionListAdapter(this, questionItemList, test.getDescription(), this);
        recyclerView.setAdapter(questionListAdapter);

        showContent();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (isNewConnection()){
            Utils.launchConnectionUseCase(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(TestActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClick(int testRate) {
        score = testRate;
        showLoading();

        String text = "{\n" +
                "\t\"description\":\""+ test.getDescription().replaceAll("\"","\\\\\"") +"\",\n" +
                "\"score\":" + score +"\n" +
                "}\n";

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), text);

        new UpdateTestUseCase(Constants.PRE_TOKEN + Utils.getTokenFromPreference(this), body)
                .execute(new UpdateTestSubscriber());
    }

    @Override
    public void onStopDialog() {
        finish();
    }

    @Override
    public void onBackPressed() {
        setReturnData();
        finish();
    }

    private void setReturnData(){
        Intent resultData = new Intent();
        resultData.putExtra("IS_UPDATED", isUpdated);
        setResult(Activity.RESULT_OK, resultData);
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

    private final class UpdateTestSubscriber extends Subscriber<Void> {
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
            isUpdated = true;
            setReturnData();
            showScoreDialog();
        }
    }
}
