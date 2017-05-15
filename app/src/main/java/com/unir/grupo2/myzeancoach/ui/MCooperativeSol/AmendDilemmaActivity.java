package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.UpdateDilemmaUseCase;
import com.unir.grupo2.myzeancoach.domain.model.CommentsCoach;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.DialogCustomFragment;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.suggestionCoachList.SuggestionListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Utils.isNewConnection;

/**
 * Created by Cesar on 30/03/2017.
 */

public class AmendDilemmaActivity extends AppCompatActivity implements DialogCustomFragment.OnStopLister{

    @BindView(R.id.coach_comments_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.content_linearLayout) LinearLayout contentLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLinearLayout;
    @BindView(R.id.error_layout) LinearLayout errorLinearLayout;

    @BindView(R.id.title_editText) EditText titleEditText;
    @BindView(R.id.description_editText) EditText descriptionEditText;
    @BindView(R.id.update_dilemma_button) Button updateDilemmaButton;

    private List<CommentsCoach> suggestionItemList;
    private SuggestionListAdapter suggestionListAdapter;

    private Dilemma dilemma;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_amend_dilemma_activity);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        dilemma = (Dilemma) intent.getParcelableExtra("DILEMMA_UPDATE");
        suggestionItemList = dilemma.getCommentsCoach();

        titleEditText.setText(dilemma.getTitle());
        descriptionEditText.setText(dilemma.getDescription());

        if (suggestionItemList != null && !suggestionItemList.isEmpty()){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            suggestionListAdapter = new SuggestionListAdapter(this, dilemma.getCommentsCoach());
            recyclerView.setAdapter(suggestionListAdapter);
        }
        showContent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
                Utils.closeSoftKeyboard(AmendDilemmaActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.update_dilemma_button)
    public void updateDilemma(Button button) {
        Utils.closeSoftKeyboard(this);
        if (titleEditText.getText().toString().trim().length() == 0 ||
                descriptionEditText.getText().toString().trim().length() == 0){
            showDialogFillOutFields();
        }else{
            showLoading();
            sendData();
        }
    }

    private void sendData(){
        showLoading();

        String userName = Utils.getUserFromPreference(this);
        String token = "Bearer " + Utils.getTokenFromPreference(this);

        String text = "{\n" +
                "\t\"username\": \"" + userName + "\",\n" +
                "\t\"title\": \"" + dilemma.getTitle() + "\",\n" +
                "\t\"date\": \"" + Utils.dateNowForBackend() + "\",\n" +
                "\t\"new_title\": \"" + titleEditText.getText().toString().replaceAll("\"","\\\\\"") + "\",\n" +
                "\t\"description\": \"" + descriptionEditText.getText().toString().replaceAll("\"","\\\\\"") + "\",\n" +
                "\t\"state\": \"waitingForAnswer\"\n" +
                "}\n";

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);

        new UpdateDilemmaUseCase(userName,token, body).execute(new UpdateDilemmaSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        errorLinearLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        loadingLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLinearLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        errorLinearLayout.setVisibility(View.GONE);
    }

    public void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        loadingLinearLayout.setVisibility(View.GONE);
        errorLinearLayout.setVisibility(View.GONE);
    }

    private void setReturnData(){
        dilemma.setTitle(titleEditText.getText().toString());
        dilemma.setDescription(descriptionEditText.getText().toString());
        dilemma.setState("waitingForAnswer");
        Intent resultData = new Intent();
        resultData.putExtra("DILEMMA_UPDATED", dilemma);
        setResult(Activity.RESULT_OK, resultData);
    }

    private void showDialogFillOutFields(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_fill_out_fields))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showFinishDialog(){
        // close existing dialog fragments
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_dialog");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        DialogCustomFragment dialog = new DialogCustomFragment();
        Bundle args = new Bundle();
        args.putString("TITLE",getString(R.string.alert_title_dilemma_updated));
        args.putString("DESCRIPTION",getString(R.string.alert_message_dilemma_sent));
        dialog.setArguments(args);
        dialog.show(manager, "fragment_dialog");
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

    private final class UpdateDilemmaSubscriber extends Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        @Override
        public void onNext(Void aVoid) {
            showFinishDialog();
            setReturnData();
        }
    }
}

