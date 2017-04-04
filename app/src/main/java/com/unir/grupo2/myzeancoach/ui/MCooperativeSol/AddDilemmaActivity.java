package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.CreateDilemmaUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.DialogCustomFragment;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Cesar on 30/03/2017.
 */

public class AddDilemmaActivity extends AppCompatActivity implements DialogCustomFragment.OnStopLister{

    @BindView(R.id.title_editText) EditText titleEditText;
    @BindView(R.id.description_editText) EditText descriptionEditText;
    @BindView(R.id.save_dilemma_button) Button saveDilemmaButton;
    @BindView(R.id.content_layout) LinearLayout contentLinearLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLinearLayout;
    @BindView(R.id.error_layout) LinearLayout errorLinearLayout;

    Dilemma dilemma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_add_dilemma_activity);
        ButterKnife.bind(this);

        showContent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(AddDilemmaActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.save_dilemma_button)
    public void saveDilemma(Button button) {
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
                "    \"username\": \"" + userName + "\",\n" +
                "    \"date\": \"" + Utils.dateNowForBackend() + "\",\n" +
                "    \"title\": \"" + titleEditText.getText().toString() + "\",\n" +
                "    \"description\": \"" + descriptionEditText.getText().toString() + "\",\n" +
                "    \"type\": \"dilema\",\n" +
                "    \"state\": \"waitingForAnswer\"\n" +
                "}\n";

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);

        new CreateDilemmaUseCase(token, body).execute(new AddDilemmaSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        errorLinearLayout.setVisibility(View.VISIBLE);
        contentLinearLayout.setVisibility(View.GONE);
        loadingLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLinearLayout.setVisibility(View.VISIBLE);
        contentLinearLayout.setVisibility(View.GONE);
        errorLinearLayout.setVisibility(View.GONE);
    }


    public void showContent() {
        contentLinearLayout.setVisibility(View.VISIBLE);
        loadingLinearLayout.setVisibility(View.GONE);
        errorLinearLayout.setVisibility(View.GONE);
    }

    private void setReturnData(Dilemma dilemmaNew){
        Intent resultData = new Intent();
        resultData.putExtra("DILEMMA_NEW", dilemmaNew);
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
        args.putString("TITLE",getString(R.string.alert_title_dilemma_sent));
        args.putString("DESCRIPTION",getString(R.string.alert_message_dilemma_sent));
        dialog.setArguments(args);
        dialog.show(manager, "fragment_dialog");
    }


    private void setReturnData(){
        Intent resultData = new Intent();
        resultData.putExtra("DILEMMA", dilemma);
        setResult(Activity.RESULT_OK, resultData);
    }

    @Override
    public void onStopDialog() {
        finish();
    }


    private final class AddDilemmaSubscriber extends Subscriber<Dilemma> {
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

        //Update listview datas
        @Override
        public void onNext(Dilemma dilemmaNew) {
            showFinishDialog();
            setReturnData(dilemmaNew);
        }
    }

}
